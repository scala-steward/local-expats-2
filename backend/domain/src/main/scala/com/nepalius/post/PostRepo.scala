package com.nepalius.post

import com.nepalius.util.Pageable
import com.nepalius.location.Location.LocationId
import Post.PostId
import zio.*

import java.time.ZonedDateTime

trait PostRepo:
  def getOne(id: PostId): Task[Option[PostWithComments]]
  def getAll(
      pageable: Pageable,
      locationId: LocationId,
  ): Task[List[PostView]]

  def getUpdated(ids: List[PostId], since: ZonedDateTime): Task[List[PostView]]

  def create(createPost: CreatePost): Task[Post]
  def addComment(
      postId: PostId,
      commentRequest: CreateComment,
  ): Task[Comment]
