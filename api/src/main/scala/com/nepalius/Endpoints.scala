package com.nepalius

import com.nepalius.user.UserServerEndpoints
import sttp.tapir.swagger.bundle.SwaggerInterpreter
import sttp.tapir.ztapir.*
import zio.{Task, ZLayer}

class Endpoints(
    userServerEndpoints: UserServerEndpoints,
) {
  val endpoints: List[ZServerEndpoint[Any, Any]] = {
    val api = userServerEndpoints.endpoints
    val docs = docsEndpoints(api)
    api ++ docs
  }

  private def docsEndpoints(
      apiEndpoints: List[ZServerEndpoint[Any, Any]],
  ): List[ZServerEndpoint[Any, Any]] =
    SwaggerInterpreter()
      .fromServerEndpoints[Task](apiEndpoints, "NepaliUS", "0.0.1-SNAPSHOT")
}

object Endpoints:
  val live: ZLayer[UserServerEndpoints, Nothing, Endpoints] =
    ZLayer.fromFunction(new Endpoints(_))
