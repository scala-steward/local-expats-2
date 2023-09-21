package com.nepalius.post.api

import com.nepalius.post.domain.Comment
import com.nepalius.post.domain.Comment.CommentId
import com.nepalius.post.domain.Post.PostId
import zio.json.{DeriveJsonEncoder, JsonEncoder}

import java.time.ZonedDateTime

case class CommentDto(
    id: CommentId,
    postId: PostId,
    message: String,
    image: Option[String],
    createdAt: ZonedDateTime,
)

object CommentDto:
  given JsonEncoder[CommentDto] = DeriveJsonEncoder.gen[CommentDto]

  def make(comment: Comment): CommentDto = CommentDto(
    comment.id,
    comment.postId,
    comment.message,
    comment.image,
    comment.createdAt,
  )
