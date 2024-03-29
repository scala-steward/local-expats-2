package com.nepalius.location.api

import com.nepalius.common.api.BaseApi
import com.nepalius.common.api.ErrorMapper.*
import com.nepalius.location.LocationMapper.toLocationDto
import com.nepalius.location.domain.LocationService
import com.nepalius.location.{LocationDto, LocationEndpoints, LocationMapper}
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
