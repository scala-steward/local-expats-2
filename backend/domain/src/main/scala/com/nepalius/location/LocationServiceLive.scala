package com.nepalius.location

import zio.*

case class LocationServiceLive private (locationRepo: LocationRepo)
    extends LocationService:
  override def getAll: Task[List[Location]] = locationRepo.getAll

object LocationServiceLive:
  val layer = ZLayer.fromFunction(LocationServiceLive.apply)
