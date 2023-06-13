package com.nepalius.user.domain

import com.nepalius.user.domain.User.UserId

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
