package com.nepalius.location.api

import com.nepalius.common.api.{BaseEndpoints, ErrorInfo}
import com.nepalius.location.LocationDto
import sttp.tapir.generic.auto.*
import sttp.tapir.json.zio.jsonBody
import sttp.tapir.PublicEndpoint
import sttp.tapir.ztapir.*
import zio.ZLayer


case class LocationEndpoints(base: BaseEndpoints):

  val getLocationsEndPoint: PublicEndpoint[Unit, ErrorInfo, List[LocationDto], Any] =
    base.publicEndpoint
      .summary("Get Locations")
      .get
      .in("api" / "locations")
      .out(jsonBody[List[LocationDto]])

object LocationEndpoints:
  // noinspection TypeAnnotation
  val live = ZLayer.fromFunction(LocationEndpoints.apply)
