package com.nepalius.post

import com.nepalius.util.Pageable
import sttp.tapir.EndpointIO.annotations.query

case class GetPostsParams(
    @query
    pageSize: Option[Int],
    @query
    lastId: Option[Long],
    @query
    locationId: Option[Long],
) {

  private val MaxPageSize = 100

  def pageable: Pageable = Pageable(
    pageSize.getOrElse(MaxPageSize).min(MaxPageSize),
    lastId.getOrElse(Long.MaxValue),
  )
}
