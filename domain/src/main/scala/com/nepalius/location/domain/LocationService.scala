package com.nepalius.location.domain

import zio.Task

trait LocationService:
  def getAll: Task[List[Location]]
