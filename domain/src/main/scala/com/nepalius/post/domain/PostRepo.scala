package com.nepalius.post.domain

import com.nepalius.post.domain.Post.PostId
import zio.*

trait PostRepo:
  def getOne(id: PostId): Task[Option[Post]]
  def getAll: Task[List[Post]]
  def create(createPost: CreatePost): Task[Post]
