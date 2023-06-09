package com.nepalius.user.domain

import zio.{Task, ZLayer}

class UserService(userRepo: UserRepo) {

  def register(user: UserRegisterData): Task[User] = {
    userRepo.create(user)
  }

}

object UserService:
  val live: ZLayer[UserRepo, Nothing, UserService] =
    ZLayer.fromFunction(new UserService(_))
