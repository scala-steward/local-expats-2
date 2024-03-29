package com.nepalius.location

import zio.Task

trait LocationRepo:
  def getAll: Task[List[Location]]
