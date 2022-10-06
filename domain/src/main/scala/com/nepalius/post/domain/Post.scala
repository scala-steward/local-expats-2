package com.nepalius.post.domain

import com.nepalius.location.State
import com.nepalius.post.domain.Post.PostId
import com.nepalius.user.domain.User.UserId

import java.time.ZonedDateTime

case class Post(
    id: PostId,
    message: String,
    targetState: State,
    targetZipCode: String,
    createdAt: ZonedDateTime,
)

object Post:
  type PostId = Long
