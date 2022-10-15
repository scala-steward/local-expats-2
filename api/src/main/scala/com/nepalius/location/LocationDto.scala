package com.nepalius.location

import com.nepalius.location.StateJsonCodec.given
import com.nepalius.location.domain.Location
import com.nepalius.location.domain.Location.LocationId
import com.nepalius.post.api.PostDto
import zio.json.{DeriveJsonEncoder, JsonEncoder}

case class LocationDto(
    id: LocationId,
    state: Option[State],
    city: Option[String],
)

object LocationDto:
  given JsonEncoder[LocationDto] = DeriveJsonEncoder.gen[LocationDto]

  def make(location: Location): LocationDto =
    LocationDto(location.id, location.state, location.city)
