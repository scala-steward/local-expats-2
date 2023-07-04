package com.nepalius.post.api

import com.nepalius.common.api.{BaseEndpoints, ErrorInfo}
import sttp.tapir.EndpointIO.annotations.query
import sttp.tapir.generic.auto.*
import sttp.tapir.json.zio.jsonBody
import sttp.tapir.ztapir.*
import sttp.tapir.{EndpointInput, PublicEndpoint}
import zio.ZLayer

final case class PostEndpoints(base: BaseEndpoints):

  val getPostsEndPoint: PublicEndpoint[GetPostsParams, ErrorInfo, List[PostDto], Any] =
    base.publicEndpoint
      .summary("Get Posts")
      .get
      .in("api" / "posts")
      .in(EndpointInput.derived[GetPostsParams])
      .out(jsonBody[List[PostDto]])

object PostEndpoints:
  // noinspection TypeAnnotation
  val live = ZLayer.fromFunction(PostEndpoints.apply)
