package com.nepalius.post.domain

import com.nepalius.location.domain.Location.LocationId
import com.nepalius.post.domain.Post.PostId
import com.nepalius.util.Pageable
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
