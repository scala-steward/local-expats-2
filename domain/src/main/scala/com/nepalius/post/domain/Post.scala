package com.nepalius.post.domain

import com.nepalius.location.State
import com.nepalius.post.domain.Post.PostId

import java.time.ZonedDateTime

case class Post(
    id: PostId,
    title: String,
    message: String,
    state: State,
    createdAt: ZonedDateTime,
)

object Post:
  type PostId = Long
