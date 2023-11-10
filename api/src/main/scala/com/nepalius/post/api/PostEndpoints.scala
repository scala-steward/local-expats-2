package com.nepalius.post.api

import com.nepalius.common.api.{BaseEndpoints, ErrorInfo}
import com.nepalius.post.domain.Post.PostId
import sttp.tapir.generic.auto.*
import sttp.tapir.json.zio.jsonBody
import sttp.tapir.ztapir.*
import sttp.tapir.{EndpointInput, PublicEndpoint}
import zio.ZLayer

case class PostEndpoints(base: BaseEndpoints):

  val getPostsEndpoint: PublicEndpoint[GetPostsParams, ErrorInfo, List[PostDto], Any] =
    base.publicEndpoint
      .summary("Get Posts")
      .get
      .in("api" / "posts")
      .in(EndpointInput.derived[GetPostsParams])
      .out(jsonBody[List[PostDto]])

  val getPostEndpoint: PublicEndpoint[PostId, ErrorInfo, PostWithCommentsDto, Any] =
    base.publicEndpoint
      .summary("Get Post")
      .get
      .in("api" / "posts" / path[PostId])
      .out(jsonBody[PostWithCommentsDto])

  val createPostEndpoint: PublicEndpoint[CreatePostDto, ErrorInfo, PostDto, Any] =
    base.publicEndpoint
      .summary("Create Post")
      .post
      .in("api" / "posts")
      .in(jsonBody[CreatePostDto])
      .out(jsonBody[PostDto])

  val addCommentEndpoint: PublicEndpoint[(PostId, CreateCommentDto), ErrorInfo, PostWithCommentsDto, Any] =
    base.publicEndpoint
      .summary("Add Comment")
      .post
      .in("api" / "posts" / path[PostId] / "comments")
      .in(jsonBody[CreateCommentDto])
      .out(jsonBody[PostWithCommentsDto])

object PostEndpoints:
  // noinspection TypeAnnotation
  val live = ZLayer.fromFunction(PostEndpoints.apply)
