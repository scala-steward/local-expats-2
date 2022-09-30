package com.nepalius.post.api

import com.nepalius.location.State
import com.nepalius.location.StateJsonCodec.given
import com.nepalius.post.domain.Post.PostId
import com.nepalius.post.domain.{Post, PostService}
import zhttp.http.*
import zhttp.http.Http.collectZIO
import zio.*
import zio.json.*

import java.time.LocalDateTime

case class PostDto(
    id: PostId,
    message: String,
    targetState: State,
    targetZipCode: String,
    createdAt: LocalDateTime,
)
object PostDto {
  given JsonCodec[PostDto] = DeriveJsonCodec.gen[PostDto]

  def make(post: Post): PostDto =
    PostDto(
      post.id,
      post.message,
      post.targetState,
      post.targetZipCode,
      post.createdAt,
    )

}

final case class PostRoutes(postService: PostService):

  val routes: Http[Any, Throwable, Request, Response] =
    collectZIO[Request] { case Method.GET -> !! / "posts" => getAll }

  private def getAll =
    postService.getAll
      .map(posts => Response.json(posts.map(PostDto.make).toJson))

object PostRoutes:
  val layer: ZLayer[PostService, Nothing, PostRoutes] =
    ZLayer.fromFunction(PostRoutes.apply)
