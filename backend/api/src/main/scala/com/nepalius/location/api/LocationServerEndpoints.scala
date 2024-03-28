package com.nepalius.location.api

import com.nepalius.common.api.ErrorMapper.*
import com.nepalius.location.LocationDto
import com.nepalius.location.domain.LocationService
import sttp.tapir.ztapir.*
import zio.*

import scala.util.chaining.*

case class LocationServerEndpoints(
    locationEndpoints: LocationEndpoints,
    locationService: LocationService,
):
  private val getLocationsServerEndpoint: ZServerEndpoint[Any, Any] =
    locationEndpoints
      .getLocationsEndPoint
      .zServerLogic(_ =>
        getLocations
          .logError
          .pipe(defaultErrorsMappings),
      )

  private def getLocations: Task[List[LocationDto]] =
    for
      locations <- locationService.getAll
      dtos = locations.map(LocationDto.make)
    yield dtos

  val endpoints: List[ZServerEndpoint[Any, Any]] = List(
    getLocationsServerEndpoint,
  )

object LocationServerEndpoints:
  // noinspection TypeAnnotation
  val layer = ZLayer.fromFunction(LocationServerEndpoints.apply)
