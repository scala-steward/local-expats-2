package com.nepalius.post

import com.nepalius.post.Comment.CommentId
import com.nepalius.post.Post.PostId

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
