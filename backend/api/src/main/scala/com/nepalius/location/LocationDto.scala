package com.nepalius.location

import com.nepalius.location.domain.Location
import com.nepalius.location.domain.Location.LocationId
import zio.json.*

case class LocationDto(
    id: LocationId,
    state: Option[String], // Using String because of max-inlines of 32 limit causing compilation error.
    city: Option[String],
)

object LocationDto:
  given JsonEncoder[LocationDto] = DeriveJsonEncoder.gen[LocationDto]
  given JsonDecoder[LocationDto] = DeriveJsonDecoder.gen[LocationDto]

  def make(location: Location): LocationDto =
    LocationDto(
      location.id,
      location.state.map(_.toString),
      location.city,
    )
