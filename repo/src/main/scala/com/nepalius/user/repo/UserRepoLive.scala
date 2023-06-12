package com.nepalius.user.repo

import com.nepalius.config.QuillContext.*
import com.nepalius.post.domain.{CreatePost, Post}
import com.nepalius.user.domain.{User, UserRegisterData, UserRepo}
import io.getquill.*
import io.getquill.extras.*
import zio.*

import java.sql.SQLException
import java.util.UUID
import javax.sql.DataSource

class UserRepoLive(
    dataSource: DataSource,
) extends UserRepo {
  private inline def queryUser = quote(querySchema[User]("users"))

  override def create(user: UserRegisterData): ZIO[Any, SQLException, User] =
    run {
      queryUser
        .insert(
          _.email -> lift(user.email),
          _.firstName -> lift(user.firstName),
          _.lastName -> lift(user.lastName),
        )
        .returningGenerated(p => p.id)
    }
      .provideEnvironment(ZEnvironment(dataSource))
      .map(id =>
        User(
          id,
          user.email,
          user.firstName,
          user.lastName,
        ),
      )

  override def findUserByEmail(email: String): ZIO[Any, SQLException, Option[User]] =
    run {
      queryUser
        .filter(_.email == lift(email))
        .take(1)
    }
      .provideEnvironment(ZEnvironment(dataSource))
      .map(_.headOption)
}

object UserRepoLive:
  val live: ZLayer[DataSource, Nothing, UserRepoLive] =
    ZLayer.fromFunction(new UserRepoLive(_))
