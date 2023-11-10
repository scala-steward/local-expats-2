package com.nepalius.post.api

import com.nepalius.post.domain.PostWithComments
import zio.json.*

case class PostWithCommentsDto(post: PostDto, comments: List[CommentDto])

object PostWithCommentsDto:
  given JsonEncoder[PostWithCommentsDto] = DeriveJsonEncoder.gen[PostWithCommentsDto]
  given JsonDecoder[PostWithCommentsDto] = DeriveJsonDecoder.gen[PostWithCommentsDto]

  def make(postWithComments: PostWithComments): PostWithCommentsDto =
    PostWithCommentsDto(
      PostDto.make(postWithComments.post),
      postWithComments.comments.map(CommentDto.make),
    )
