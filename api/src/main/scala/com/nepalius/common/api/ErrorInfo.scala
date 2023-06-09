package com.nepalius.common.api

import com.nepalius.common.*
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

sealed trait ErrorInfo
case class BadRequest(error: String = "Bad request.") extends ErrorInfo
case class Unauthorized(error: String = "Unauthorized.") extends ErrorInfo
case class Forbidden(error: String = "Forbidden.") extends ErrorInfo
case class NotFound(error: String = "Not found.") extends ErrorInfo
case class Conflict(error: String = "Conflict.") extends ErrorInfo
case class ValidationFailed(errors: Map[String, List[String]]) extends ErrorInfo
case class InternalServerError(error: String = "Internal server error.") extends ErrorInfo

object ErrorInfo:
  given badRequestEncoder: JsonEncoder[BadRequest] = DeriveJsonEncoder.gen[BadRequest]
  given badRequestDecoder: JsonDecoder[BadRequest] = DeriveJsonDecoder.gen[BadRequest]
  given forbiddenEncoder: JsonEncoder[Forbidden] = DeriveJsonEncoder.gen[Forbidden]
  given forbiddenDecoder: JsonDecoder[Forbidden] = DeriveJsonDecoder.gen[Forbidden]
  given notFoundEncoder: JsonEncoder[NotFound] = DeriveJsonEncoder.gen[NotFound]
  given notFoundDecoder: JsonDecoder[NotFound] = DeriveJsonDecoder.gen[NotFound]
  given conflictEncoder: JsonEncoder[Conflict] = DeriveJsonEncoder.gen[Conflict]
  given conflictDecoder: JsonDecoder[Conflict] = DeriveJsonDecoder.gen[Conflict]
  given unauthorizedEncoder: JsonEncoder[Unauthorized] = DeriveJsonEncoder.gen[Unauthorized]
  given unauthorizedDecoder: JsonDecoder[Unauthorized] = DeriveJsonDecoder.gen[Unauthorized]
  given validationFailedEncoder: JsonEncoder[ValidationFailed] = DeriveJsonEncoder.gen[ValidationFailed]
  given validationFailedDecoder: JsonDecoder[ValidationFailed] = DeriveJsonDecoder.gen[ValidationFailed]
  given internalServerErrorEncoder: JsonEncoder[InternalServerError] = DeriveJsonEncoder.gen[InternalServerError]
  given internalServerErrorDecoder: JsonDecoder[InternalServerError] = DeriveJsonDecoder.gen[InternalServerError]
