package com.nepalius.location

import com.nepalius.location.LocationMapper.toLocationDto
import com.nepalius.util.BaseApi
import com.nepalius.util.ErrorMapper.*
import sttp.tapir.ztapir.*
import zio.*

import scala.collection.immutable.List
import scala.util.chaining.*

final case class LocationApi(
    locationService: LocationService,
) extends BaseApi
    with LocationEndpoints:

  override def endpoints = List(getLocationsServerEndpoint)

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

object LocationApi:
  val layer = ZLayer.fromFunction(LocationApi.apply)
