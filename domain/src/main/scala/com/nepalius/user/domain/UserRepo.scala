package com.nepalius.user.domain

import zio.Task

trait UserRepo {

  def save(user: UserRegisterData): Task[User]
}
