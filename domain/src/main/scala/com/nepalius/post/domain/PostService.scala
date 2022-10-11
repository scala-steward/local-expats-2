package com.nepalius.post.domain

import com.nepalius.post.domain.Post.PostId
import com.nepalius.util.Pageable
import com.nepalius.location.State
import zio.*

trait PostService:
  def getOne(id: PostId): Task[Option[Post]]
  def getAll(pageable: Pageable, state: State): Task[List[Post]]
  def create(postRequest: CreatePost): Task[Post]
