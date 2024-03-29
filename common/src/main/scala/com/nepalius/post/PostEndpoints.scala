package com.nepalius.post

import com.nepalius.common.{BaseEndpoints, ErrorInfo}
import sttp.tapir.*
import sttp.tapir.generic.auto.*
import sttp.tapir.json.zio.jsonBody

trait PostEndpoints extends BaseEndpoints:

  val getPostsEndpoint
      : PublicEndpoint[GetPostsParams, ErrorInfo, List[PostDto], Any] =
    publicEndpoint
      .tag("Posts")
      .name("Get Posts name")
      .summary("Get Posts")
      .get
      .in("api" / "posts")
      .in(EndpointInput.derived[GetPostsParams])
      .out(jsonBody[List[PostDto]])

  val getPostEndpoint
      : PublicEndpoint[Long, ErrorInfo, PostWithCommentsDto, Any] =
    publicEndpoint
      .tag("Posts")
      .summary("Get Post")
      .get
      .in("api" / "posts" / path[Long]("id"))
      .out(jsonBody[PostWithCommentsDto])

  val createPostEndpoint
      : PublicEndpoint[CreatePostDto, ErrorInfo, PostDto, Any] =
    publicEndpoint
      .tag("Posts")
      .summary("Create Post")
      .post
      .in("api" / "posts")
      .in(jsonBody[CreatePostDto])
      .out(jsonBody[PostDto])

  val addCommentEndpoint: PublicEndpoint[
    (Long, CreateCommentDto),
    ErrorInfo,
    PostWithCommentsDto,
    Any,
  ] =
    publicEndpoint
      .tag("Posts")
      .summary("Add Comment")
      .post
      .in("api" / "posts" / path[Long]("id") / "comments")
      .in(jsonBody[CreateCommentDto])
      .out(jsonBody[PostWithCommentsDto])
