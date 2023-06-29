package com.nepalius.location.domain

import zio.*

final case class LocationServiceLive(locationRepo: LocationRepo)
    extends LocationService:
  override def getAll: Task[List[Location]] = locationRepo.getAll

object LocationServiceLive:
  // noinspection TypeAnnotation
  val live = ZLayer.fromFunction(LocationServiceLive.apply)
