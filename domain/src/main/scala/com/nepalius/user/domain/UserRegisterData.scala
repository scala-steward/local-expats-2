package com.nepalius.user.domain

case class UserRegisterData(
    email: String,
    firstName: String,
    lastName: String,
    passwordHash: String,
)
