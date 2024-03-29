package com.nepalius.location

import com.nepalius.common.{BaseEndpoints, ErrorInfo}
import sttp.tapir.*
import sttp.tapir.generic.auto.*
import sttp.tapir.json.zio.jsonBody

trait LocationEndpoints extends BaseEndpoints:

  val getLocationsEndPoint
      : PublicEndpoint[Unit, ErrorInfo, List[LocationDto], Any] =
    publicEndpoint
      .tag("Locations")
      .summary("Get Locations")
      .get
      .in("api" / "locations")
      .out(jsonBody[List[LocationDto]])
