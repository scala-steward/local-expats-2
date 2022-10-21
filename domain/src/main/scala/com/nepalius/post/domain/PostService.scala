package com.nepalius.post.domain

import com.nepalius.location.State
import com.nepalius.location.domain.Location.LocationId
import com.nepalius.post.domain.Post.PostId
import com.nepalius.util.Pageable
import zio.*

import java.time.ZonedDateTime

trait PostService:
  def getOne(id: PostId): Task[Option[PostWithComments]]
  def getAll(
      pageable: Pageable,
      locationId: LocationId,
  ): Task[List[Post]]

  def getUpdated(ids: List[PostId], since: ZonedDateTime): Task[List[Post]]

  def create(postRequest: CreatePost): Task[Post]
  def addComment(
      postId: PostId,
      commentRequest: CreateComment,
  ): Task[PostWithComments]
