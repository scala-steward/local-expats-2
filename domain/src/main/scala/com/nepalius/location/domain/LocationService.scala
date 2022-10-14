package com.nepalius.location.domain

import com.nepalius.location.State
import com.nepalius.post.domain.Post
import com.nepalius.util.Pageable
import zio.Task

trait LocationService:
  def getAll: Task[List[Location]]
