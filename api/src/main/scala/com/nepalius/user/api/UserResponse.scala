package com.nepalius.user.api

import com.nepalius.user.api.UserResponse
import com.nepalius.user.domain.User
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

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

  def apply(user: User): UserResponse = UserResponse(
    user.id,
    user.data.email,
    user.data.firstName,
    user.data.lastName,
  )
