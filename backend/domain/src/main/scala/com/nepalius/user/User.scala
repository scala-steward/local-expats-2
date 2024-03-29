package com.nepalius.user

import User.UserId

import java.util.UUID

case class UserData(
    email: String,
    firstName: String,
    lastName: String,
    passwordHash: String,
)

case class User(
    id: UserId,
    data: UserData,
)

object User:
  type UserId = UUID
