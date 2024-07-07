package com.nepalius.ui

import com.nepalius.post.*
import com.raquo.airstream.core.EventStream
import org.scalajs.macrotaskexecutor.MacrotaskExecutor.Implicits.*
import sttp.tapir.*

object PostApiClient
    extends ApiClient
    with PostEndpoints:

  def getPosts: EventStream[List[PostDto]] =
    val requestTemplate = client.toClientThrowErrors(
      getPostsEndpoint,
      None,
      backend,
    )
    val result = requestTemplate.apply(GetPostsParams())
    EventStream.fromFuture(result)
