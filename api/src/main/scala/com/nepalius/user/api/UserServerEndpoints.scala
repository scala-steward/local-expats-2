package com.nepalius.user.api

import com.nepalius.auth.AuthService
import com.nepalius.common.Exceptions
import com.nepalius.common.Exceptions.Unauthorized
import com.nepalius.user.api.ErrorMapper.*
import com.nepalius.common.api.*
import com.nepalius.user.api.{UserEndpoints, UserResponse, UserServerEndpoints}
import com.nepalius.user.domain.UserService

import sttp.tapir.ztapir.*
import zio.*
import zio.Console.*

import java.util.UUID
import scala.util.chaining.*

class UserServerEndpoints(userEndpoints: UserEndpoints, userService: UserService, authService: AuthService):

  private val registerServerEndpoints: ZServerEndpoint[Any, Any] =
    userEndpoints
      .registerEndPoint
      .zServerLogic(
        registerUser(_)
          .logError
          .pipe(defaultErrorsMappings),
      )

  private val loginServerEndpoints: ZServerEndpoint[Any, Any] =
    userEndpoints
      .loginEndpoint
      .zServerLogic(
        loginUser(_)
          .logError
          .pipe(defaultErrorsMappings),
      )

  private val getCurrentUserServerEndpoints: ZServerEndpoint[Any, Any] =
    userEndpoints
      .getCurrentUserEndpoint
      .serverLogic(session =>
        _ =>
          userService.get(session.userId)
            .logError
            .mapError {
              case e: Exceptions.NotFound => NotFound(e.message)
              case _                      => InternalServerError()
            }
            .map(UserResponse.apply),
      )

  private def registerUser(user: UserRegisterPayload): Task[UserWithAuthTokenResponse] =
    for
      passwordHash <- authService.encryptPassword(user.password)
      userWithPasswordHash = user.copy(password = passwordHash)
      user <- userService.register(userWithPasswordHash.toData)
      token <- authService.generateJwt(user.email)
    yield UserWithAuthTokenResponse(UserResponse(user), token)

  private def loginUser(userCredentials: UserLoginPayload): Task[UserWithAuthTokenResponse] =
    for
      maybeUser <- userService.findUserByEmail(userCredentials.email)
      user <- ZIO.fromOption(maybeUser)
        .mapError(_ => Unauthorized())
      token <- authService.generateJwt(user.email)
    yield UserWithAuthTokenResponse(UserResponse(user), token)

  val endpoints: List[ZServerEndpoint[Any, Any]] = List(
    registerServerEndpoints,
    loginServerEndpoints,
    getCurrentUserServerEndpoints,
  )

object UserServerEndpoints:
  val live: ZLayer[UserEndpoints with UserService with AuthService, Nothing, UserServerEndpoints] = ZLayer
    .fromFunction(new UserServerEndpoints(_, _, _))
