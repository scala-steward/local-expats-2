package com.nepalius

import com.nepalius.post.api.PostServerEndpoints
import com.nepalius.user.api.UserServerEndpoints
import sttp.tapir.swagger.bundle.SwaggerInterpreter
import sttp.tapir.ztapir.*
import zio.{Task, ZLayer}

final case class Endpoints(
    userServerEndpoints: UserServerEndpoints,
    postServerEndpoints: PostServerEndpoints,
) {
  val endpoints: List[ZServerEndpoint[Any, Any]] = {
    val api = userServerEndpoints.endpoints ++ postServerEndpoints.endpoints
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
  // noinspection TypeAnnotation
  val live = ZLayer.fromFunction(Endpoints.apply)
