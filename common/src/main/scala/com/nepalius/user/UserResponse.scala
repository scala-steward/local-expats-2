package com.nepalius.user

import zio.json.*

import java.util.UUID

case class UserResponse(
    id: UUID,
    email: String,
    firstName: String,
    lastName: String,
)

object UserResponse:
  given JsonEncoder[UserResponse] = DeriveJsonEncoder.gen[UserResponse]
  given JsonDecoder[UserResponse] = DeriveJsonDecoder.gen[UserResponse]
