package com.nepalius.location

import zio.Task

trait LocationService:
  def getAll: Task[List[Location]]
