package com.nepalius.common.api

import com.nepalius.auth.{AuthService, UserSession}
import com.nepalius.common.*
import com.nepalius.common.api.BaseEndpoints.{UserWithEmailNotFoundMessage, defaultErrorOutputs}
import com.nepalius.user.domain.User.UserId
import com.nepalius.user.domain.UserService
import sttp.model.StatusCode
import sttp.tapir.generic.auto.*
import sttp.tapir.json.zio.jsonBody
import sttp.tapir.ztapir.*
import sttp.tapir.{EndpointOutput, PublicEndpoint}
import zio.*

case class BaseEndpoints(authService: AuthService, userService: UserService):
  val publicEndpoint: PublicEndpoint[Unit, ErrorInfo, Unit, Any] =
    endpoint
      .errorOut(defaultErrorOutputs)

  val secureEndpoint: ZPartialServerEndpoint[Any, String, UserSession, Unit, ErrorInfo, Unit, Any] =
    endpoint
      .errorOut(defaultErrorOutputs)
      .securityIn(auth.bearer[String]())
      .zServerSecurityLogic[Any, UserSession](handleAuth)

  private def handleAuth(token: String): IO[ErrorInfo, UserSession] =
    (for
      userEmail <- authService.verifyJwt(token)
      userId <- userIdByEmail(userEmail)
    yield UserSession(userId))
      .logError
      .mapError {
        case e: Exceptions.Unauthorized => Unauthorized(e.message)
        case e: Exceptions.NotFound     => NotFound(e.message)
        case _                          => InternalServerError()
      }

  private def userIdByEmail(email: String): Task[UserId] =
    userService.findUserByEmail(email)
      .someOrFail(Exceptions.NotFound(UserWithEmailNotFoundMessage(email)))
      .map(_.id)

object BaseEndpoints:
  // noinspection TypeAnnotation
  val live = ZLayer.fromFunction(BaseEndpoints.apply)

  private val UserWithEmailNotFoundMessage: String => String = (email: String) => s"User with email $email doesn't exist"

  val defaultErrorOutputs: EndpointOutput.OneOf[ErrorInfo, ErrorInfo] =
    oneOf[ErrorInfo](
      oneOfVariant(statusCode(StatusCode.BadRequest).and(jsonBody[BadRequest])),
      oneOfVariant(statusCode(StatusCode.Forbidden).and(jsonBody[Forbidden])),
      oneOfVariant(statusCode(StatusCode.NotFound).and(jsonBody[NotFound])),
      oneOfVariant(statusCode(StatusCode.Conflict).and(jsonBody[Conflict])),
      oneOfVariant(statusCode(StatusCode.Unauthorized).and(jsonBody[Unauthorized])),
      oneOfVariant(statusCode(StatusCode.UnprocessableEntity).and(jsonBody[ValidationFailed])),
      oneOfVariant(statusCode(StatusCode.InternalServerError).and(jsonBody[InternalServerError])),
    )
