package com.nepalius.location

object LocationMapper:
  def toLocationDto(location: Location) =
    LocationDto(
      location.id,
      location.state.map(_.toString),
      location.city,
    )
