package com.nepalius.post.domain

import com.nepalius.location.State
import com.nepalius.util.Pageable
import com.nepalius.post.domain.Post.PostId
import zio.*

trait PostRepo:
  def getOne(id: PostId): Task[Option[PostWithComments]]
  def getAll(pageable: Pageable, state: State): Task[List[Post]]
  def create(createPost: CreatePost): Task[Post]
  def addComment(
      postId: PostId,
      commentRequest: CreateComment,
  ): Task[Comment]
