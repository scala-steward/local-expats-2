package com.nepalius.post

case class CreateComment(
    message: String,
    image: Option[String],
)
