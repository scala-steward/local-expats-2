package com.nepalius.post.api

import com.nepalius.location.StateJsonCodec.given
import com.nepalius.location.domain.Location
import com.nepalius.location.domain.Location.LocationId
import com.nepalius.location.{LocationDto, State}
import com.nepalius.post.domain.Post
import com.nepalius.post.domain.Post.PostId
import zio.*
import zio.json.*

import java.time.ZonedDateTime

case class PostDto(
    id: PostId,
    title: String,
    message: Option[String],
    locationId: LocationId,
    createdAt: ZonedDateTime,
)

object PostDto {
  given JsonEncoder[PostDto] = DeriveJsonEncoder.gen[PostDto]

  def make(post: Post): PostDto =
    PostDto(
      post.id,
      post.title,
      post.message,
      post.locationId,
      post.createdAt,
    )

}
