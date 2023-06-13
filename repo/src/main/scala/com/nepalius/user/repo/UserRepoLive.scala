package com.nepalius.user.repo

import com.nepalius.config.QuillContext
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

case class UserRow(
    id: UserId,
    email: String,
    firstName: String,
    lastName: String,
    passwordHash: String,
) {
  def toUser: User = User(id, UserData(email, firstName, lastName, passwordHash))
}

class UserRepoLive(
    quill: QuillContext,
) extends UserRepo:
  import quill.*

  private inline def queryUser = quote(querySchema[UserRow]("users"))

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
          UserData(
            user.email,
            user.firstName,
            user.lastName,
            user.passwordHash,
          ),
        ),
      )

  override def findUserByEmail(email: String): ZIO[Any, SQLException, Option[User]] =
    run {
      queryUser
        .filter(_.email == lift(email))
    }
      .map(_.headOption)
      .map(row => row.map(_.toUser()))

  override def findUserById(id: UserId): Task[Option[User]] =
    run {
      queryUser
        .filter(_.id == lift(id))
    }
      .map(_.headOption)
      .map(row => row.map(_.toUser()))

  override def update(id: UserId, userData: UserData): Task[User] = {
    val user = UserRow(id, userData.email, userData.firstName, userData.lastName, userData.passwordHash)
    run {
      queryUser
        .filter(_.id == lift(id))
        .updateValue(lift(user))
    }
      .map(_ => user.toUser())
  }

object UserRepoLive:
  val live: ZLayer[QuillContext, Nothing, UserRepoLive] =
    ZLayer.fromFunction(new UserRepoLive(_))
