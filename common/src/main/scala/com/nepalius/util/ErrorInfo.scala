package com.nepalius.util

import zio.json.*

sealed trait ErrorInfo
case class BadRequest(error: String = "Bad request.") extends ErrorInfo
case class Unauthorized(error: String = "Unauthorized.") extends ErrorInfo
case class Forbidden(error: String = "Forbidden.") extends ErrorInfo
case class NotFound(error: String = "Not found.") extends ErrorInfo
case class Conflict(error: String = "Conflict.") extends ErrorInfo
case class ValidationFailed(errors: Map[String, List[String]]) extends ErrorInfo
case class InternalServerError(error: String = "Internal server error.")
    extends ErrorInfo

object ErrorInfo:
  given JsonEncoder[BadRequest] = DeriveJsonEncoder.gen
  given JsonDecoder[BadRequest] = DeriveJsonDecoder.gen
  given JsonEncoder[Forbidden] = DeriveJsonEncoder.gen
  given JsonDecoder[Forbidden] = DeriveJsonDecoder.gen
  given JsonEncoder[NotFound] = DeriveJsonEncoder.gen
  given JsonDecoder[NotFound] = DeriveJsonDecoder.gen
  given JsonEncoder[Conflict] = DeriveJsonEncoder.gen
  given JsonDecoder[Conflict] = DeriveJsonDecoder.gen
  given JsonEncoder[Unauthorized] = DeriveJsonEncoder.gen
  given JsonDecoder[Unauthorized] = DeriveJsonDecoder.gen
  given JsonEncoder[ValidationFailed] = DeriveJsonEncoder.gen
  given JsonDecoder[ValidationFailed] = DeriveJsonDecoder.gen
  given JsonEncoder[InternalServerError] = DeriveJsonEncoder.gen
  given JsonDecoder[InternalServerError] = DeriveJsonDecoder.gen
