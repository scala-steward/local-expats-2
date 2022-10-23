package com.nepalius.post.api

import com.nepalius.post.domain.CreateComment
import zio.json.{DeriveJsonDecoder, JsonDecoder}

case class CreateCommentDto(
    message: String,
    image: Option[String],
):
  def toCreateComment: CreateComment = CreateComment(message, image)

object CreateCommentDto:
  given JsonDecoder[CreateCommentDto] = DeriveJsonDecoder.gen[CreateCommentDto]
