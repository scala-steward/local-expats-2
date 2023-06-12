package com.nepalius.user.api

import com.nepalius.auth.AuthService
import com.nepalius.user.api.ErrorMapper.*
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

  private def registerUser(user: UserRegisterPayload): Task[UserResponse] =
    for
      passwordHash <- authService.encryptPassword(user.password)
      userWithPasswordHash = user.copy(password = passwordHash)
      user <- userService.register(userWithPasswordHash.toData)
    yield UserResponse(user)

  val endpoints: List[ZServerEndpoint[Any, Any]] = List(registerServerEndpoints)

object UserServerEndpoints:
  val live: ZLayer[UserEndpoints with UserService with AuthService, Nothing, UserServerEndpoints] = ZLayer
    .fromFunction(new UserServerEndpoints(_, _, _))
