package com.nepalius.location

import zio.json.*

case class LocationDto(
    id: Long,
    state: Option[String], // Using String because of max-inlines of 32 limit causing compilation error.
    city: Option[String],
)

object LocationDto:
  given JsonEncoder[LocationDto] = DeriveJsonEncoder.gen[LocationDto]
  given JsonDecoder[LocationDto] = DeriveJsonDecoder.gen[LocationDto]