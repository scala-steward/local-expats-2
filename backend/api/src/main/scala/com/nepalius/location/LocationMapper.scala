package com.nepalius.location

import com.nepalius.location.domain.*

object LocationMapper:
  def toLocationDto(location: Location) =
    LocationDto(
      location.id,
      location.state.map(_.toString),
      location.city,
    )
