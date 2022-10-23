package com.nepalius.post.domain

import com.nepalius.post.domain.Comment.CommentId
import com.nepalius.post.domain.Post.PostId

import java.time.ZonedDateTime
import scala.Option

case class Comment(
    id: CommentId,
    postId: PostId,
    message: String,
    image: Option[String],
    createdAt: ZonedDateTime,
)

object Comment:
  type CommentId = Long
