package com.nepalius.user

import com.nepalius.user.User.UserId

case class UserUpdateData(
    id: UserId,
    email: Option[String],
    passwordHash: Option[String],
    firstName: String,
    lastName: String,
)
