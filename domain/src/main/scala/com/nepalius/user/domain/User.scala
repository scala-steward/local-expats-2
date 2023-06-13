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
    email: String,
    firstName: String,
    lastName: String,
    passwordHash: String,
)

object User:
  type UserId = UUID
