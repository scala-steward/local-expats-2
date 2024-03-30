package com.nepalius.post

import com.nepalius.util.Pageable
import com.nepalius.location.Location.{DefaultLocationId, LocationId}
import Post.PostId
import zio.*

import java.time.ZonedDateTime

case class PostServiceLive(postRepo: PostRepo) extends PostService:

  override def getOne(id: PostId): Task[Option[PostWithComments]] =
    postRepo.getOne(id)

  override def getAll(
      pageable: Pageable,
      locationId: Option[LocationId],
  ): Task[List[PostView]] =
    postRepo.getAll(pageable, locationId.getOrElse(DefaultLocationId))

  override def getUpdated(
      ids: List[PostId],
      since: ZonedDateTime,
  ): Task[List[PostView]] = postRepo.getUpdated(ids, since)

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
  // noinspection TypeAnnotation
  val layer = ZLayer.fromFunction(PostServiceLive.apply)
