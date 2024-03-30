package com.nepalius.location

import com.nepalius.util.BaseApi
import com.nepalius.util.ErrorMapper.*
import com.nepalius.location.LocationMapper.toLocationDto
import sttp.tapir.ztapir.*
import zio.*

import scala.util.chaining.*

final case class LocationApi(
    locationService: LocationService,
) extends BaseApi
    with LocationEndpoints:

  private val getLocationsServerEndpoint: ZServerEndpoint[Any, Any] =
    getLocationsEndPoint
      .zServerLogic(_ =>
        getLocations
          .logError
          .pipe(defaultErrorsMappings),
      )

  private def getLocations: Task[List[LocationDto]] =
    for
      locations <- locationService.getAll
      dtos = locations.map(toLocationDto)
    yield dtos

  override val endpoints: List[ZServerEndpoint[Any, Any]] =
    List(
      getLocationsServerEndpoint,
    )

object LocationApi:
  // noinspection TypeAnnotation
  val layer = ZLayer.fromFunction(LocationApi.apply)
