package com.nepalius.post.domain

import com.nepalius.post.domain.PostService
import com.nepalius.user.domain.User
import zio.*

import javax.sql.DataSource

class PostServiceLive(postRepo: PostRepo) extends PostService:
  override def getAll: Task[List[Post]] = postRepo.getAll

object PostServiceLive:
  val layer: ZLayer[PostRepo, Nothing, PostService] =
    ZLayer.fromFunction(PostServiceLive(_))
