package com.nepalius.post.api

import com.nepalius.post.domain.CreateComment
import zio.json.{DeriveJsonDecoder, JsonDecoder}

case class CreateCommentDto(message: String):
  def toCreateComment: CreateComment = CreateComment(message)

object CreateCommentDto:
  given JsonDecoder[CreateCommentDto] = DeriveJsonDecoder.gen[CreateCommentDto]
