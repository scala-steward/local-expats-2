package com.nepalius.util

import com.nepalius.util.BaseEndpoints.defaultErrorOutputs
import sttp.model.StatusCode
import sttp.tapir.generic.auto.*
import sttp.tapir.json.zio.jsonBody
import sttp.tapir.*
import sttp.tapir.{Endpoint, EndpointOutput, PublicEndpoint}

trait BaseEndpoints:

  type SecureEndpoint[INPUT, ERROR_OUTPUT, OUTPUT, -R] =
    Endpoint[String, INPUT, ERROR_OUTPUT, OUTPUT, R]

  val publicEndpoint: PublicEndpoint[Unit, ErrorInfo, Unit, Any] =
    endpoint
      .errorOut(defaultErrorOutputs)

  val secureEndpoint: SecureEndpoint[Unit, ErrorInfo, Unit, Any] =
    endpoint
      .errorOut(defaultErrorOutputs)
      .securityIn(auth.bearer[String]())

object BaseEndpoints:
  val defaultErrorOutputs: EndpointOutput.OneOf[ErrorInfo, ErrorInfo] =
    oneOf[ErrorInfo](
      oneOfVariant(statusCode(StatusCode.BadRequest).and(jsonBody[BadRequest])),
      oneOfVariant(statusCode(StatusCode.Forbidden).and(jsonBody[Forbidden])),
      oneOfVariant(statusCode(StatusCode.NotFound).and(jsonBody[NotFound])),
      oneOfVariant(statusCode(StatusCode.Conflict).and(jsonBody[Conflict])),
      oneOfVariant(
        statusCode(StatusCode.Unauthorized).and(jsonBody[Unauthorized]),
      ),
      oneOfVariant(statusCode(StatusCode.UnprocessableEntity).and(
        jsonBody[ValidationFailed],
      )),
      oneOfVariant(statusCode(StatusCode.InternalServerError).and(
        jsonBody[InternalServerError],
      )),
    )
