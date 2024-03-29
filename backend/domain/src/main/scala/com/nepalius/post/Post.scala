package com.nepalius.post

import com.nepalius.location.Location.LocationId
import Post.PostId
import com.nepalius.location.Location

import java.time.ZonedDateTime

case class Post(
    id: PostId,
    title: String,
    message: Option[String],
    locationId: LocationId,
    createdAt: ZonedDateTime,
    image: Option[String],
)

object Post:
  type PostId = Long
