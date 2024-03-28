package com.nepalius.post.api

import com.nepalius.location.domain.Location.LocationId
import com.nepalius.post.domain.Post.PostId
import com.nepalius.util.Pageable
import sttp.tapir.EndpointIO.annotations.query

case class GetPostsParams(
    @query
    pageSize: Option[Int],
    @query
    lastId: Option[PostId],
    @query
    locationId: Option[LocationId],
) {

  private val MaxPageSize = 100

  def pageable: Pageable = Pageable(
    pageSize.getOrElse(MaxPageSize).min(MaxPageSize),
    lastId.getOrElse(Long.MaxValue),
  )
}
