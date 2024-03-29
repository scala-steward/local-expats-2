package com.nepalius.user

import sttp.tapir.Schema.annotations.validate
import sttp.tapir.Validator
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

case class UserRegisterPayload(
    @validate(Validator.nonEmptyString) email: String,
    @validate(Validator.minLength(2)) firstName: String,
    @validate(Validator.minLength(2)) lastName: String,
    @validate(Validator.nonEmptyString) password: String,
)

object UserRegisterPayload:
  given JsonEncoder[UserRegisterPayload] =
    DeriveJsonEncoder.gen[UserRegisterPayload]

  given JsonDecoder[UserRegisterPayload] =
    DeriveJsonDecoder.gen[UserRegisterPayload]
