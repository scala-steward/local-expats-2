package com.nepalius.user

import com.nepalius.user.User.UserId
import zio.Task

trait UserRepo {
  def create(user: UserRegisterData): Task[User]
  def update(id: UserId, user: UserData): Task[User]
  def findUserById(id: UserId): Task[Option[User]]
  def findUserByEmail(email: String): Task[Option[User]]
}
