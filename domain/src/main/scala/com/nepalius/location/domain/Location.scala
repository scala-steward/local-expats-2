package com.nepalius.location.domain

import com.nepalius.location.State
import com.nepalius.location.domain.Location.LocationId

case class Location(id: LocationId, state: Option[State], city: Option[String])

object Location:
  type LocationId = Long
  val defaultLocationId = 1L // US
