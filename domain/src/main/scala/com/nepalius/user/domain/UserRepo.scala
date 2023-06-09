package com.nepalius.user.domain

import zio.Task

trait UserRepo {

  def create(user: UserRegisterData): Task[User]
}
