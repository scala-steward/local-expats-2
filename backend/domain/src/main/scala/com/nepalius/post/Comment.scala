package com.nepalius.post

import Comment.CommentId
import Post.PostId

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
