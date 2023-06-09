package com.nepalius.user.repo

import com.nepalius.user.domain.{User, UserRegisterData, UserRepo}
import zio.{Task, ZLayer, ZIO}

import java.util.UUID

class UserRepoLive extends UserRepo {
  override def save(user: UserRegisterData): Task[User] =
    ZIO.succeed(User(UUID.randomUUID(), user.email, user.firstName, user.lastName))
}

object UserRepoLive:
  val live: ZLayer[Any, Nothing, UserRepoLive] =
    ZLayer.fromFunction(() => new UserRepoLive)
