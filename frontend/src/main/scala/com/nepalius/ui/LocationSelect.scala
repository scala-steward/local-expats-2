package com.nepalius.ui

import com.nepalius.location.LocationDto
import com.raquo.laminar.api.L.*

def LocationSelect(): HtmlElement =
  val locations$ = Fetch.get[List[LocationDto]]("locations")
  val locationsSorted$ = locations$.map(_.sortBy(l => (l.state, l.city)))

  def toOptionLabel(location: LocationDto) =
    location match
      case LocationDto(_, Some(state), Some(city)) => s"$state / $city"
      case LocationDto(_, Some(state), None)       => state
      case LocationDto(_, None, None)              => "United States"
      case _ => throw new IllegalStateException("Invalid Location")

  def toOption(location: LocationDto) =
    option(
      value(location.id.toString),
      toOptionLabel(location),
    )

  select(
    children <-- locationsSorted$.map(_.map(toOption)),
  )
