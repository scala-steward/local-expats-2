package com.nepalius.post.domain

import com.nepalius.post.domain.Post.PostId
import com.nepalius.post.domain.PostService
import com.nepalius.user.domain.User
import zio.*

import java.time.LocalDateTime
import java.util.UUID
import javax.sql.DataSource

final case class PostServiceLive(postRepo: PostRepo) extends PostService:
  override def getOne(id: PostId): Task[Option[Post]] = postRepo.getOne(id)
  override def getAll: Task[List[Post]] = postRepo.getAll
  override def create(createPost: CreatePost): Task[Post] =
    postRepo.create(createPost)

object PostServiceLive:
  val layer: ZLayer[PostRepo, Nothing, PostService] =
    ZLayer.fromFunction(PostServiceLive.apply)
