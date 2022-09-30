package com.nepalius.post.domain

import zio.*

trait PostRepo:
  def getAll: Task[List[Post]]
