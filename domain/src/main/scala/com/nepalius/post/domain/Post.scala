package com.nepalius.post.domain

import com.nepalius.location.State
import com.nepalius.location.domain.Location
import com.nepalius.location.domain.Location.LocationId
import com.nepalius.post.domain.Post.PostId

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
