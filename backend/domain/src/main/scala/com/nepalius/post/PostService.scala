package com.nepalius.post

import com.nepalius.util.Pageable
import com.nepalius.location.Location.LocationId
import Post.PostId
import zio.*

import java.time.ZonedDateTime

trait PostService:
  def getOne(id: PostId): Task[Option[PostWithComments]]
  def getAll(
      pageable: Pageable,
      locationId: Option[LocationId],
  ): Task[List[PostView]]

  def getUpdated(ids: List[PostId], since: ZonedDateTime): Task[List[PostView]]

  def create(postRequest: CreatePost): Task[Post]
  def addComment(
      postId: PostId,
      commentRequest: CreateComment,
  ): Task[PostWithComments]
