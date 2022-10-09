package com.nepalius.post.api
import com.nepalius.location.State
import com.nepalius.location.StateJsonCodec.given
import com.nepalius.post.domain.CreatePost
import zio.*
import zio.json.*

case class CreatePostDto(
    title: String,
    message: String,
    targetState: State,
):
  def toCreatePost: CreatePost =
    CreatePost(title, message, targetState)

object CreatePostDto:
  given JsonDecoder[CreatePostDto] = DeriveJsonDecoder.gen[CreatePostDto]
