package com.nepalius.post.api

import com.nepalius.location.StateJsonCodec.given
import com.nepalius.location.domain.Location
import com.nepalius.location.domain.Location.LocationId
import com.nepalius.location.{LocationDto, State}
import com.nepalius.post.domain.{Post, PostView}
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
    image: Option[String],
    noOfComments: Int,
)

object PostDto {
  given JsonEncoder[PostDto] = DeriveJsonEncoder.gen[PostDto]
  given JsonDecoder[PostDto] = DeriveJsonDecoder.gen[PostDto]

  def make(post: Post): PostDto =
    make(PostView.fromPost(post, 0))

  def make(post: PostView): PostDto =
    PostDto(
      post.id,
      post.title,
      post.message,
      post.locationId,
      post.createdAt,
      post.image,
      post.noOfComments,
    )
}
