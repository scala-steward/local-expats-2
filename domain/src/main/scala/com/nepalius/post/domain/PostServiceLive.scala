package com.nepalius.post.domain

import com.nepalius.location.State
import com.nepalius.location.domain.Location.LocationId
import com.nepalius.util.Pageable
import com.nepalius.post.domain.Post.PostId
import com.nepalius.post.domain.PostService
import zio.*

import java.time.LocalDateTime
import java.util.UUID
import javax.sql.DataSource

final case class PostServiceLive(postRepo: PostRepo) extends PostService:
  override def getOne(id: PostId): Task[Option[PostWithComments]] =
    postRepo.getOne(id)
  override def getAll(
      pageable: Pageable,
      locationId: Option[LocationId],
  ): Task[List[Post]] =
    postRepo.getAll(pageable, locationId)
  override def create(createPost: CreatePost): Task[Post] =
    postRepo.create(createPost)

  override def addComment(
      postId: PostId,
      commentRequest: CreateComment,
  ): Task[PostWithComments] =
    for
      _ <- postRepo.addComment(postId, commentRequest)
      post <- getOne(postId)
    yield post.get

object PostServiceLive:
  val layer: ZLayer[PostRepo, Nothing, PostService] =
    ZLayer.fromFunction(PostServiceLive.apply)
