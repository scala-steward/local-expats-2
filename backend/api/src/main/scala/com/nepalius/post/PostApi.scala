package com.nepalius.post

import com.nepalius.post.Post.PostId
import com.nepalius.post.PostMapper.*
import com.nepalius.util.ErrorMapper.*
import com.nepalius.util.PageableMapper.toPageable
import com.nepalius.util.{BaseApi, Exceptions, PageableMapper}
import sttp.tapir.ztapir.*
import zio.*

import scala.util.chaining.*

final case class PostApi(
    postService: PostService,
) extends BaseApi
    with PostEndpoints:

  override def endpoints = List(
    getPostsServerEndpoint,
    getPostServerEndpoint,
    createPostServerEndpoint,
    addCommentServerEndpoint,
  )

  private val getPostsServerEndpoint: ZServerEndpoint[Any, Any] =
    getPostsEndpoint
      .zServerLogic(
        getPosts(_)
          .logError
          .pipe(defaultErrorsMappings),
      )

  private val getPostServerEndpoint: ZServerEndpoint[Any, Any] =
    getPostEndpoint
      .zServerLogic(
        getOne(_)
          .logError
          .pipe(defaultErrorsMappings),
      )

  private val createPostServerEndpoint: ZServerEndpoint[Any, Any] =
    createPostEndpoint
      .zServerLogic(
        createPost(_)
          .logError
          .pipe(defaultErrorsMappings),
      )

  private val addCommentServerEndpoint: ZServerEndpoint[Any, Any] =
    addCommentEndpoint
      .zServerLogic((postId, body) =>
        addComment(postId, body)
          .logError
          .pipe(defaultErrorsMappings),
      )

  private def getPosts(params: GetPostsParams): Task[List[PostDto]] =
    for
      posts <-
        postService.getAll(toPageable(params.pageable), params.locationId)
      dtos = posts.map(toPostDto)
    yield dtos

  private def getOne(id: PostId): Task[PostWithCommentsDto] =
    for
      post <- postService.getOne(id)
      dto = post.map(toPostWithCommentsDto)
    yield dto.getOrElse(throw Exceptions.NotFound("Post not found for id: $id"))

  private def createPost(dto: CreatePostDto) =
    for post <- postService.create(toCreatePost(dto))
    yield toPostDto(post)

  private def addComment(postId: PostId, dto: CreateCommentDto) =
    for updatedPost <- postService.addComment(postId, toCreateComment(dto))
    yield toPostWithCommentsDto(updatedPost)

object PostApi:
  val layer = ZLayer.fromFunction(PostApi.apply)
