package com.nepalius.user

case class UserRegisterData(
    email: String,
    firstName: String,
    lastName: String,
    passwordHash: String,
)
