package com.nepalius.user.api

import com.nepalius.user.api.UserRegisterPayload
import com.nepalius.user.domain.UserRegisterData
import sttp.tapir.Schema.annotations.validate
import sttp.tapir.Validator
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

case class UserLoginPayload(
    @validate(Validator.nonEmptyString) email: String,
    @validate(Validator.nonEmptyString) password: String,
)

object UserLoginPayload:
  given JsonEncoder[UserLoginPayload] = DeriveJsonEncoder.gen[UserLoginPayload]
  given JsonDecoder[UserLoginPayload] = DeriveJsonDecoder.gen[UserLoginPayload]
