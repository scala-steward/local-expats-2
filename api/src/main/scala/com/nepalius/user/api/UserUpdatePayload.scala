package com.nepalius.user.api

import sttp.tapir.Schema.annotations.{validate, validateEach}
import sttp.tapir.Validator
import zio.json.*

case class UserUpdatePayload(
    @validateEach(Validator.nonEmptyString) email: Option[String],
    @validateEach(Validator.nonEmptyString) password: Option[String],
    @validate(Validator.minLength(2)) firstName: String,
    @validate(Validator.minLength(2)) lastName: String,
)

object UserUpdatePayload:
  given JsonEncoder[UserUpdatePayload] = DeriveJsonEncoder.gen[UserUpdatePayload]
  given JsonDecoder[UserUpdatePayload] = DeriveJsonDecoder.gen[UserUpdatePayload]
