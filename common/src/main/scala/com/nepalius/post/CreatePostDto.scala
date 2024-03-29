package com.nepalius.post

import zio.*
import zio.json.*

case class CreatePostDto(
    title: String,
    message: Option[String],
    locationId: Long,
    image: Option[String],
)

object CreatePostDto:
  given JsonEncoder[CreatePostDto] = DeriveJsonEncoder.gen
  given JsonDecoder[CreatePostDto] = DeriveJsonDecoder.gen
