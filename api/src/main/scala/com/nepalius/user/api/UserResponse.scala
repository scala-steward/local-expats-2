package com.nepalius.user.api

import com.nepalius.user.api.UserResponse
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

import java.util.UUID

case class UserResponse(
    id: UUID,
    email: String,
    firstName: String,
    lastName: String,
    token: Option[String],
)

object UserResponse:
  given userEncoder: JsonEncoder[UserResponse] =
    DeriveJsonEncoder.gen[UserResponse]

  given userDecoder: JsonDecoder[UserResponse] =
    DeriveJsonDecoder.gen[UserResponse]
