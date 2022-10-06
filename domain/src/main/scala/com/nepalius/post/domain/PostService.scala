package com.nepalius.post.domain

import com.nepalius.post.domain.Post.PostId
import com.nepalius.user.domain.User
import zio.*

trait PostService:
  def getOne(id: PostId): Task[Option[Post]]
  def getAll: Task[List[Post]]
  def create(postRequest: CreatePost): Task[Post]