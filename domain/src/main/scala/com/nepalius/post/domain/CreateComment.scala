package com.nepalius.post.domain

case class CreateComment(
    message: String,
    image: Option[String],
)
