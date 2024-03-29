package com.nepalius.post

import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonDecoder, JsonEncoder}

case class CreateCommentDto(
    message: String,
    image: Option[String],
)

object CreateCommentDto:
  given JsonEncoder[CreateCommentDto] = DeriveJsonEncoder.gen
  given JsonDecoder[CreateCommentDto] = DeriveJsonDecoder.gen
