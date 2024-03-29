package com.nepalius.post

import zio.json.*

import java.time.ZonedDateTime

case class CommentDto(
    id: Long,
    postId: Long,
    message: String,
    image: Option[String],
    createdAt: ZonedDateTime,
)

object CommentDto:
  given JsonEncoder[CommentDto] = DeriveJsonEncoder.gen[CommentDto]
  given JsonDecoder[CommentDto] = DeriveJsonDecoder.gen[CommentDto]
