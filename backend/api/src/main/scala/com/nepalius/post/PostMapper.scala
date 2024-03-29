package com.nepalius.post

import com.nepalius.post.*

object PostMapper:

  def toPostDto(post: Post): PostDto =
    toPostDto(PostView.fromPost(post, 0))

  def toPostDto(post: PostView): PostDto =
    PostDto(
      post.id,
      post.title,
      post.message,
      post.locationId,
      post.createdAt,
      post.image,
      post.noOfComments,
    )

  def toCreatePost(dto: CreatePostDto): CreatePost =
    CreatePost(dto.title, dto.message, dto.locationId, dto.image)

  def toCreateComment(dto: CreateCommentDto): CreateComment =
    CreateComment(dto.message, dto.image)

  def toCommentDto(comment: Comment): CommentDto =
    CommentDto(
      comment.id,
      comment.postId,
      comment.message,
      comment.image,
      comment.createdAt,
    )

  def toPostWithCommentsDto(postWithComments: PostWithComments)
      : PostWithCommentsDto =
    PostWithCommentsDto(
      toPostDto(postWithComments.post),
      postWithComments.comments.map(toCommentDto),
    )
