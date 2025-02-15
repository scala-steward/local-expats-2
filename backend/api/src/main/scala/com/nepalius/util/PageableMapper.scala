package com.nepalius.util

object PageableMapper:
  def toPageable(pageableDto: PageableDto) =
    Pageable(
      pageableDto.pageSize,
      pageableDto.lastId,
    )
