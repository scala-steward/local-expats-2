package com.nepalius.post.api
import com.nepalius.location.State
import com.nepalius.location.StateJsonCodec.given
import com.nepalius.location.domain.Location
import com.nepalius.location.domain.Location.LocationId
import com.nepalius.post.domain.CreatePost
import zio.*
import zio.json.*

case class CreatePostDto(
    title: String,
    message: Option[String],
    locationId: LocationId,
    image: Option[String],
):
  def toCreatePost: CreatePost =
    CreatePost(title, message, locationId, image)

object CreatePostDto:
  given JsonDecoder[CreatePostDto] = DeriveJsonDecoder.gen[CreatePostDto]
