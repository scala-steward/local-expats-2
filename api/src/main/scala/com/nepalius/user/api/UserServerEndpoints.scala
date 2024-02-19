package com.nepalius.user.api

import com.nepalius.auth.AuthService
import com.nepalius.common.Exceptions
import com.nepalius.common.Exceptions.Unauthorized
import com.nepalius.common.api.*
import com.nepalius.common.api.ErrorMapper.*
import com.nepalius.user.domain.{UserService, UserUpdateData}
import sttp.tapir.ztapir.*
import zio.*

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
            .mapBoth(
              {
                case e: Exceptions.NotFound => NotFound(e.message)
                case _                      => InternalServerError()
              },
              UserResponse.apply,
            ),
      )

  private val updateCurrentUserServerEndpoints: ZServerEndpoint[Any, Any] =
    userEndpoints
      .updateUserEndpoint
      .serverLogic(session =>
        payload =>
          (for
            mayBeNewPassword <- payload.password
              .map(authService.encryptPassword(_).asSome)
              .getOrElse(ZIO.succeed(None))
            userUpdateData = UserUpdateData(session.userId, payload.email, mayBeNewPassword, payload.firstName, payload.lastName)
            updatedUser <- userService.updateUser(userUpdateData)
          yield UserResponse(updatedUser))
            .logError
            .mapError {
              case e: Exceptions.NotFound     => NotFound(e.message)
              case e: Exceptions.AlreadyInUse => Conflict(e.message)
              case _                          => InternalServerError()
            },
      )

  private def registerUser(user: UserRegisterPayload): Task[UserWithAuthTokenResponse] =
    for
      passwordHash <- authService.encryptPassword(user.password)
      userWithPasswordHash = user.copy(password = passwordHash)
      user <- userService.register(userWithPasswordHash.toData)
      token <- authService.generateJwt(user.data.email)
    yield UserWithAuthTokenResponse(UserResponse(user), token)

  private def loginUser(userCredentials: UserLoginPayload): Task[UserWithAuthTokenResponse] =
    for
      maybeUser <- userService.findUserByEmail(userCredentials.email)
      user <- ZIO.fromOption(maybeUser).orElseFail(Unauthorized())
      _ <- authService.verifyPassword(userCredentials.password, user.data.passwordHash)
      token <- authService.generateJwt(user.data.email)
    yield UserWithAuthTokenResponse(UserResponse(user), token)

  val endpoints: List[ZServerEndpoint[Any, Any]] = List(
    registerServerEndpoints,
    loginServerEndpoints,
    getCurrentUserServerEndpoints,
    updateCurrentUserServerEndpoints,
  )

object UserServerEndpoints:
  val layer: ZLayer[UserEndpoints with UserService with AuthService, Nothing, UserServerEndpoints] = ZLayer
    .fromFunction(new UserServerEndpoints(_, _, _))
