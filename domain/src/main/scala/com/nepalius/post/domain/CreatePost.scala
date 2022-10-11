package com.nepalius.post.domain

import com.nepalius.location.State

case class CreatePost(
    title: String,
    message: Option[String],
    state: State,
)
