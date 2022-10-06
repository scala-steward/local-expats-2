package com.nepalius.post.domain

import com.nepalius.location.State

case class CreatePost(
    message: String,
    targetState: State,
    targetZipCode: String,
)
