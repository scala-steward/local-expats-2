package com.nepalius.post.api

import com.nepalius.api.ApiUtils
import com.nepalius.api.ApiUtils.parseBody
import com.nepalius.location.State
import com.nepalius.post.domain.Post.PostId
import com.nepalius.post.domain.{Post, PostService}
import zhttp.http.*
import zhttp.http.Http.collectZIO
import zhttp.http.Method.{GET, POST}
import zio.*
import zio.json.*

import java.time.LocalDateTime

final case class PostRoutes(postService: PostService):

  val routes: Http[Any, Throwable, Request, Response] =
    collectZIO[Request] {
      case GET -> !! / "api" / "posts"        => getAll
      case req @ POST -> !! / "api" / "posts" => createPost(req)
    }

  private def createPost(req: Request) = {
    for
      dto <- parseBody[CreatePostDto](req)
      postRequest = dto.toCreatePost
      createdPost <- postService.create(postRequest)
      postDto = PostDto.make(createdPost)
    yield Response.json(postDto.toJson)
  }

  private def getAll =
    for
      posts <- postService.getAll
      dtos = posts.map(PostDto.make)
    yield Response.json(dtos.toJson)

object PostRoutes:
  val layer: ZLayer[PostService, Nothing, PostRoutes] =
    ZLayer.fromFunction(PostRoutes.apply)
