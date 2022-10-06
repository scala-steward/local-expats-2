package com.nepalius.post.api

import com.nepalius.location.State
import com.nepalius.location.StateJsonCodec.given
import com.nepalius.post.domain.Post
import com.nepalius.post.domain.Post.PostId
import zio.*
import zio.json.*

import java.time.LocalDateTime

case class PostDto(
    id: PostId,
    message: String,
    targetState: State,
    targetZipCode: String,
    createdAt: LocalDateTime,
)

object PostDto {
  given JsonEncoder[PostDto] = DeriveJsonEncoder.gen[PostDto]

  def make(post: Post): PostDto =
    PostDto(
      post.id,
      post.message,
      post.targetState,
      post.targetZipCode,
      post.createdAt,
    )

}
