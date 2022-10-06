package com.nepalius.post.api

import com.nepalius.util.ApiUtils
import com.nepalius.util.ApiUtils.parseBody
import com.nepalius.util.Pageable
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
      case req @ GET -> !! / "api" / "posts"      => getAll(req)
      case GET -> !! / "api" / "posts" / long(id) => getOne(id)
      case req @ POST -> !! / "api" / "posts"     => createPost(req)
    }

  private def createPost(req: Request) = {
    for
      dto <- parseBody[CreatePostDto](req)
      postRequest = dto.toCreatePost
      createdPost <- postService.create(postRequest)
      postDto = PostDto.make(createdPost)
    yield Response.json(postDto.toJson)
  }

  private def getAll(req: Request) =
    for
      posts <- postService.getAll(ApiUtils.parsePageable(req))
      dtos = posts.map(PostDto.make)
    yield Response.json(dtos.toJson)

  private def getOne(id: PostId) =
    for
      post <- postService.getOne(id)
      dto = post.map(PostDto.make)
    yield dto
      .map(d => Response.json(d.toJson))
      .getOrElse(Response.status(Status.NotFound))

object PostRoutes:
  val layer: ZLayer[PostService, Nothing, PostRoutes] =
    ZLayer.fromFunction(PostRoutes.apply)
