package com.nepalius.post

import com.nepalius.util.PageableDto
import sttp.tapir.EndpointIO.annotations.query

case class GetPostsParams(
    @query
    pageSize: Option[Int] = None,
    @query
    lastId: Option[Long] = None,
    @query
    locationId: Option[Long] = None,
) {

  private val MaxPageSize = 100

  def pageable = PageableDto(
    pageSize.getOrElse(MaxPageSize).min(MaxPageSize),
    lastId.getOrElse(Long.MaxValue),
  )
}
