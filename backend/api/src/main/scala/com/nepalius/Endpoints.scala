package com.nepalius

import com.nepalius.common.api.BaseApi
import com.nepalius.location.api.LocationApi
import com.nepalius.post.api.PostApi
import com.nepalius.user.api.UserApi
import sttp.tapir.swagger.bundle.SwaggerInterpreter
import sttp.tapir.ztapir.*
import zio.{Task, ZLayer}

final case class Endpoints(
    userApi: UserApi,
    postApi: PostApi,
    locationApi: LocationApi,
) {
  val endpoints: List[ZServerEndpoint[Any, Any]] =
    val apis: List[BaseApi] = List(userApi, postApi, locationApi)
    val apiEndpoints = apis.flatMap(_.endpoints)
    apiEndpoints ++ docsEndpoints(apiEndpoints)

  private def docsEndpoints(
      apiEndpoints: List[ZServerEndpoint[Any, Any]],
  ): List[ZServerEndpoint[Any, Any]] =
    SwaggerInterpreter()
      .fromServerEndpoints[Task](apiEndpoints, "NepaliUS", "0.0.1-SNAPSHOT")
}

object Endpoints:
  // noinspection TypeAnnotation
  val layer = ZLayer.fromFunction(Endpoints.apply)
