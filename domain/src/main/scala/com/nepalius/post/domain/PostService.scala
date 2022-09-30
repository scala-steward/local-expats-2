package com.nepalius.post.domain

import com.nepalius.user.domain.User
import zio.*

trait PostService:
  def getAll: zio.Task[List[Post]]
