package com.nepalius.post.api

import com.nepalius.common.api.ErrorMapper.*
import com.nepalius.post.domain.PostService
import com.nepalius.util.Pageable
import sttp.tapir.ztapir.*
import zio.*

import scala.util.chaining.*

final case class PostServerEndpoints(
    postEndpoints: PostEndpoints,
    postService: PostService,
):
  private val getPostsServerEndpoint: ZServerEndpoint[Any, Any] =
    postEndpoints
      .getPostsEndPoint
      .zServerLogic((pageable, filters) =>
        getPosts(pageable, filters)
          .logError
          .pipe(defaultErrorsMappings),
      )

  private def getPosts(pageable: Pageable, filters: PostFilters): Task[List[PostDto]] =
    for
      posts <- postService.getAll(pageable, filters.locationId)
      dtos = posts.map(PostDto.make)
    yield dtos

  val endpoints: List[ZServerEndpoint[Any, Any]] = List(
    getPostsServerEndpoint,
  )

object PostServerEndpoints:
  // noinspection TypeAnnotation
  val live = ZLayer.fromFunction(PostServerEndpoints.apply)
