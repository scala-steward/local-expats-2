package com.nepalius.post.api

import com.nepalius.location.State
import com.nepalius.post.domain.Post.PostId
import com.nepalius.location.domain.Location.LocationId
import com.nepalius.post.domain.{Post, PostService}
import com.nepalius.util.ApiUtils.parseBody
import com.nepalius.util.{ApiUtils, Pageable}
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
      case req @ POST -> !! / "api" / "posts" / long(id) / "comments" =>
        addComment(id, req)
    }

  private def createPost(req: Request) = {
    for
      dto <- parseBody[CreatePostDto](req)
      postRequest = dto.toCreatePost
      createdPost <- postService.create(postRequest)
      postDto = PostDto.make(createdPost)
    yield Response.json(postDto.toJson)
  }

  private def getAll(req: Request) = {
    val pageable = ApiUtils.parsePageable(req)
    val locationIdParam =
      req.url.queryParams.get("locationId").flatMap(_.headOption)
    val locationId =
      locationIdParam.map(_.toLong).getOrElse(1L) // 1 = US Location
    for
      posts <- postService.getAll(pageable, locationId)
      dtos = posts.map(PostDto.make)
    yield Response.json(dtos.toJson)
  }

  private def getOne(id: PostId) =
    for
      post <- postService.getOne(id)
      dto = post.map(PostWithCommentsDto.make)
    yield dto
      .map(d => Response.json(d.toJson))
      .getOrElse(Response.status(Status.NotFound))

  private def addComment(postId: PostId, req: Request) = {
    for
      dto <- parseBody[CreateCommentDto](req)
      commentRequest = dto.toCreateComment
      updatedPost <- postService.addComment(postId, commentRequest)
      dto = PostWithCommentsDto.make(updatedPost)
    yield Response.json(dto.toJson)
  }

object PostRoutes:
  val layer: ZLayer[PostService, Nothing, PostRoutes] =
    ZLayer.fromFunction(PostRoutes.apply)
