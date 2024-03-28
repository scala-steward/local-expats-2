package com.nepalius.user.domain

import com.nepalius.user.domain.User.UserId

case class UserUpdateData(
    id: UserId,
    email: Option[String],
    passwordHash: Option[String],
    firstName: String,
    lastName: String,
)
