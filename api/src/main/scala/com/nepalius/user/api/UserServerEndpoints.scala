package com.nepalius.user.api

import com.nepalius.user.api.{UserEndpoints, UserResponse, UserServerEndpoints}
import com.nepalius.user.api.ErrorMapper.*
import com.nepalius.user.domain.UserService
import sttp.tapir.ztapir.*
import zio.*
import zio.Console.*
import scala.util.chaining.*

import java.util.UUID

class UserServerEndpoints(userEndpoints: UserEndpoints, userService: UserService):

  private val registerServerEndpoints: ZServerEndpoint[Any, Any] =
    userEndpoints.registerEndPoint
      .zServerLogic(payload =>
        userService
          .register(payload.toData)
          .map(UserResponse(_))
          .logError
          .pipe(defaultErrorsMappings),
      )

  val endpoints: List[ZServerEndpoint[Any, Any]] = List(registerServerEndpoints)

object UserServerEndpoints:
  val live: ZLayer[UserEndpoints with UserService, Nothing, UserServerEndpoints] =
    ZLayer.fromFunction(new UserServerEndpoints(_, _))
