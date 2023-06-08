package com.nepalius.user

import com.nepalius.common.ErrorInfo
import sttp.tapir.Endpoint
import sttp.tapir.generic.auto.*
import sttp.tapir.json.zio.jsonBody
import sttp.tapir.ztapir.*
import zio.ZLayer

class UserEndpoints:

  val registerEndPoint
      : Endpoint[Unit, UserRegisterData, Unit, UserResponse, Any] =
    endpoint.post
      .in("api" / "users")
      .in(jsonBody[UserRegisterData])
      .out(jsonBody[UserResponse])

object UserEndpoints:
  val live: ZLayer[Any, Nothing, UserEndpoints] =
    ZLayer.fromFunction(() => new UserEndpoints())
