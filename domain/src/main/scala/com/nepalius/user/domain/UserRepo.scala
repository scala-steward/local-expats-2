package com.nepalius.user.domain

import zio.Task

trait UserRepo {

  def create(user: UserRegisterData): Task[User]

  def findUserByEmail(email: String): Task[Option[User]]
}
