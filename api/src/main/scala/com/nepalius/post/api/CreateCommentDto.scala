package com.nepalius.post.api

import com.nepalius.post.domain.CreateComment
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

case class CreateCommentDto(
    message: String,
    image: Option[String],
):
  def toCreateComment: CreateComment = CreateComment(message, image)

object CreateCommentDto:
  given JsonEncoder[CreateCommentDto] = DeriveJsonEncoder.gen[CreateCommentDto]
  given JsonDecoder[CreateCommentDto] = DeriveJsonDecoder.gen[CreateCommentDto]
