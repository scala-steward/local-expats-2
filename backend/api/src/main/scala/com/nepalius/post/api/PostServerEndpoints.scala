package com.nepalius.post.api

import com.nepalius.common.api.ErrorMapper.*
import com.nepalius.common.Exceptions
import com.nepalius.post.domain.Post.PostId
import com.nepalius.post.domain.PostService
import sttp.tapir.ztapir.*
import zio.*

import scala.util.chaining.*

case class PostServerEndpoints(
    postEndpoints: PostEndpoints,
    postService: PostService,
):
  private val getPostsServerEndpoint: ZServerEndpoint[Any, Any] =
    postEndpoints
      .getPostsEndpoint
      .zServerLogic(
        getPosts(_)
          .logError
          .pipe(defaultErrorsMappings),
      )

  private val getPostServerEndpoint: ZServerEndpoint[Any, Any] =
    postEndpoints
      .getPostEndpoint
      .zServerLogic(
        getOne(_)
          .logError
          .pipe(defaultErrorsMappings),
      )

  private val createPostServerEndpoint: ZServerEndpoint[Any, Any] =
    postEndpoints
      .createPostEndpoint
      .zServerLogic(
        createPost(_)
          .logError
          .pipe(defaultErrorsMappings),
      )

  private val addCommentServerEndpoint: ZServerEndpoint[Any, Any] =
    postEndpoints
      .addCommentEndpoint
      .zServerLogic((postId, body) =>
        addComment(postId, body)
          .logError
          .pipe(defaultErrorsMappings),
      )

  private def getPosts(params: GetPostsParams): Task[List[PostDto]] =
    for
      posts <- postService.getAll(params.pageable, params.locationId)
      dtos = posts.map(PostDto.make)
    yield dtos

  private def getOne(id: PostId): Task[PostWithCommentsDto] =
    for
      post <- postService.getOne(id)
      dto = post.map(PostWithCommentsDto.make)
    yield dto.getOrElse(throw Exceptions.NotFound("Post not found for id: $id"))

  private def createPost(dto: CreatePostDto) = {
    for
      createdPost <- postService.create(dto.toCreatePost)
      postDto = PostDto.make(createdPost)
    yield postDto
  }

  private def addComment(postId: PostId, dto: CreateCommentDto) = {
    for
      updatedPost <- postService.addComment(postId, dto.toCreateComment)
      dto = PostWithCommentsDto.make(updatedPost)
    yield dto
  }

  val endpoints: List[ZServerEndpoint[Any, Any]] = List(
    getPostsServerEndpoint,
    getPostServerEndpoint,
    createPostServerEndpoint,
    addCommentServerEndpoint,
  )

object PostServerEndpoints:
  // noinspection TypeAnnotation
  val layer = ZLayer.fromFunction(PostServerEndpoints.apply)
