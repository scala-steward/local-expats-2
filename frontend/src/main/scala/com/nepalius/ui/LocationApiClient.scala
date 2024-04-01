package com.nepalius.ui

import com.nepalius.location.*
import com.raquo.airstream.core.EventStream
import org.scalajs.macrotaskexecutor.MacrotaskExecutor.Implicits.*
import sttp.tapir.*

object LocationApiClient
    extends ApiClient
    with LocationEndpoints:

  def getLocations: EventStream[List[LocationDto]] =
    val requestTemplate = client.toClientThrowErrors(
      getLocationsEndPoint,
      None,
      backend,
    )
    val result = requestTemplate.apply(())
    EventStream.fromFuture(result)
