package com.nepalius.location.domain

import zio.Task

trait LocationRepo:
  def getAll: Task[List[Location]]
