package com.nepalius.location

import com.nepalius.location.domain.LocationService
import zio.*
import zio.http.*
import zio.http.Method.GET
import zio.json.*

case class LocationRoutes(locationService: LocationService):
  val routes: Http[Any, Throwable, Request, Response] =
    Http.collectZIO[Request] { case GET -> Root / "api" / "locations" => getAll }

  private def getAll =
    for
      locations <- locationService.getAll
      dtos = locations.map(LocationDto.make)
    yield Response.json(dtos.toJson)

object LocationRoutes:
  // noinspection TypeAnnotation
  val live = ZLayer.fromFunction(LocationRoutes.apply)
