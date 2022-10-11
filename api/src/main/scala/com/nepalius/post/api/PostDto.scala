package com.nepalius.post.api

import com.nepalius.location.State
import com.nepalius.location.StateJsonCodec.given
import com.nepalius.post.domain.Post
import com.nepalius.post.domain.Post.PostId
import zio.*
import zio.json.*

import java.time.ZonedDateTime

case class PostDto(
    id: PostId,
    title: String,
    message: Option[String],
    state: State,
    createdAt: ZonedDateTime,
)

object PostDto {
  given JsonEncoder[PostDto] = DeriveJsonEncoder.gen[PostDto]

  def make(post: Post): PostDto =
    PostDto(
      post.id,
      post.title,
      post.message,
      post.state,
      post.createdAt,
    )

}
