package com.nepalius.post.api
import com.nepalius.location.State
import com.nepalius.location.StateJsonCodec.given
import com.nepalius.post.domain.CreatePost
import zio.*
import zio.json.*

case class CreatePostDto(
    message: String,
    targetState: State,
    targetZipCode: String,
):
  def toCreatePost: CreatePost = CreatePost(message, targetState, targetZipCode)

object CreatePostDto:
  given JsonDecoder[CreatePostDto] = DeriveJsonDecoder.gen[CreatePostDto]
