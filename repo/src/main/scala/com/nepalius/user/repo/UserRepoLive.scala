package com.nepalius.user.repo

import com.nepalius.config.QuillContext.QuillPostgres
import com.nepalius.post.domain.{CreatePost, Post}
import com.nepalius.user.domain.User.UserId
import com.nepalius.user.domain.{User, UserData, UserRegisterData, UserRepo}
import io.getquill.*
import io.getquill.extras.*
import io.getquill.jdbczio.Quill
import zio.*

import java.sql.SQLException
import java.util.UUID
import javax.sql.DataSource

class UserRepoLive(
    quill: QuillPostgres,
) extends UserRepo:
  import quill.*

  private inline def queryUser = quote(querySchema[User]("users"))

  override def create(user: UserRegisterData): ZIO[Any, SQLException, User] =
    run {
      queryUser
        .insert(
          _.email -> lift(user.email),
          _.firstName -> lift(user.firstName),
          _.lastName -> lift(user.lastName),
          _.passwordHash -> lift(user.passwordHash),
        )
        .returningGenerated(p => p.id)
    }
      .map(id =>
        User(
          id,
          user.email,
          user.firstName,
          user.lastName,
          user.passwordHash,
        ),
      )

  override def findUserByEmail(email: String): ZIO[Any, SQLException, Option[User]] =
    run {
      queryUser
        .filter(_.email == lift(email))
    }
      .map(_.headOption)

  override def findUserById(id: UserId): Task[Option[User]] =
    run {
      queryUser
        .filter(_.id == lift(id))
    }
      .map(_.headOption)

  override def update(id: UserId, userData: UserData): Task[User] = {
    val user = User(id, userData.email, userData.firstName, userData.lastName, userData.passwordHash)
    run {
      queryUser
        .filter(_.id == lift(id))
        .updateValue(lift(user))
    }
      .map(_ => user)
  }

object UserRepoLive:
  val live: ZLayer[QuillPostgres, Nothing, UserRepoLive] =
    ZLayer.fromFunction(new UserRepoLive(_))
