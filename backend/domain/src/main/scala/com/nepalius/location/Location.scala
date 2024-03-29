package com.nepalius.location

import com.nepalius.location.Location.LocationId

case class Location(id: LocationId, state: Option[State], city: Option[String])

object Location:
  type LocationId = Long
  val DefaultLocationId = 1L // US
