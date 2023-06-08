package com.nepalius.user
import sttp.tapir.ztapir.*
import zio.*
import zio.Console.*

import java.util.UUID

class UserServerEndpoints(userEndpoints: UserEndpoints):

  private val registerServerEndpoints: ZServerEndpoint[Any, Any] =
    userEndpoints.registerEndPoint
      .zServerLogic(data =>
        ZIO.succeed(
          UserResponse(
            UUID.randomUUID(),
            data.email,
            data.firstName,
            data.lastName,
            None,
          ),
        ),
      )

  val endpoints: List[ZServerEndpoint[Any, Any]] = List(registerServerEndpoints)

object UserServerEndpoints:
  val live: ZLayer[UserEndpoints, Nothing, UserServerEndpoints] =
    ZLayer.fromFunction(new UserServerEndpoints(_))
