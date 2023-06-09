package com.nepalius.user.api

import com.nepalius.user.api.UserRegisterPayload
import com.nepalius.user.domain.UserRegisterData
import sttp.tapir.Schema.annotations.validate
import sttp.tapir.Validator
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

case class UserRegisterPayload(
    @validate(Validator.nonEmptyString) email: String,
    @validate(Validator.minLength(2)) firstName: String,
    @validate(Validator.minLength(2)) lastName: String,
    @validate(Validator.nonEmptyString) password: String,
) {
  def toData: UserRegisterData = UserRegisterData(
    email,
    firstName,
    lastName,
    password,
  )
}

object UserRegisterPayload:
  given UserRegisterRequestEncoder: JsonEncoder[UserRegisterPayload] =
    DeriveJsonEncoder.gen[UserRegisterPayload]

  given UserRegisterRequestDecoder: JsonDecoder[UserRegisterPayload] =
    DeriveJsonDecoder.gen[UserRegisterPayload]
