package com.nepalius.post.domain

import com.nepalius.location.State

case class CreatePost(
    title: String,
    message: String,
    targetState: State,
)
