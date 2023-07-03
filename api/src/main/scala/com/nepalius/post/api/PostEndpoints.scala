package com.nepalius.post.api

import com.nepalius.common.api.{BaseEndpoints, ErrorInfo}
import com.nepalius.location.domain.Location.DefaultLocationId
import com.nepalius.util.Pageable
import sttp.tapir.generic.auto.*
import sttp.tapir.json.zio.jsonBody
import sttp.tapir.ztapir.*
import sttp.tapir.{Endpoint, EndpointInput}
import zio.ZLayer

final case class PostEndpoints(base: BaseEndpoints):
  private val MaxPageSize = 100
  private val pageableParams: EndpointInput[Pageable] =
    query[Option[Int]]("pageSize")
      .and(
        query[Option[Long]]("lastId"),
      )
      .map(params =>
        Pageable(
          params(0).getOrElse(MaxPageSize).min(MaxPageSize),
          params(1).getOrElse(Long.MaxValue),
        ),
      )(pageable => (Some(pageable.pageSize), Some(pageable.lastId)))

  private val filterParams: EndpointInput[PostFilters] =
    query[Option[Long]]("locationId")
      .map(locationIdOp => PostFilters(locationIdOp.getOrElse(DefaultLocationId)))(filters => Some(filters.locationId))

  val getPostsEndPoint: Endpoint[Unit, (Pageable, PostFilters), ErrorInfo, List[PostDto], Any] =
    base.publicEndpoint
      .summary("Get Posts")
      .get
      .in("api" / "posts")
      .in(pageableParams)
      .in(filterParams)
      .out(jsonBody[List[PostDto]])

object PostEndpoints:
  // noinspection TypeAnnotation
  val live = ZLayer.fromFunction(PostEndpoints.apply)
