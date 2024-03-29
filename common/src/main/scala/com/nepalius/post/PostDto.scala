package com.nepalius.post

import zio.json.*

import java.time.ZonedDateTime

case class PostDto(
    id: Long,
    title: String,
    message: Option[String],
    locationId: Long,
    createdAt: ZonedDateTime,
    image: Option[String],
    noOfComments: Int,
)

object PostDto {
  given JsonEncoder[PostDto] = DeriveJsonEncoder.gen[PostDto]
  given JsonDecoder[PostDto] = DeriveJsonDecoder.gen[PostDto]
}
