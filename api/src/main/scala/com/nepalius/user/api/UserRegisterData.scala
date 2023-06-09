package com.nepalius.user.api

import com.nepalius.user.api.UserRegisterData
import sttp.tapir.Schema.annotations.validate
import sttp.tapir.Validator
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

case class UserRegisterData(
    @validate(Validator.nonEmptyString) email: String,
    @validate(Validator.minLength(2)) firstName: String,
    @validate(Validator.minLength(2)) lastName: String,
    @validate(Validator.nonEmptyString) password: String,
)

object UserRegisterData:
  given userRegisterDataEncoder: JsonEncoder[UserRegisterData] =
    DeriveJsonEncoder.gen[UserRegisterData]

  given userRegisterDataDecoder: JsonDecoder[UserRegisterData] =
    DeriveJsonDecoder.gen[UserRegisterData]
