package com.nepalius.user

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

case class UserWithAuthTokenResponse(user: UserResponse, authToken: String)

object UserWithAuthTokenResponse:
  given JsonEncoder[UserWithAuthTokenResponse] =
    DeriveJsonEncoder.gen[UserWithAuthTokenResponse]
  given JsonDecoder[UserWithAuthTokenResponse] =
    DeriveJsonDecoder.gen[UserWithAuthTokenResponse]
