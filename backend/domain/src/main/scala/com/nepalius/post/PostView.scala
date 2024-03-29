package com.nepalius.post

import com.nepalius.location.Location.LocationId
import Post.PostId

import java.time.ZonedDateTime

case class PostView(
    id: PostId,
    title: String,
    message: Option[String],
    locationId: LocationId,
    createdAt: ZonedDateTime,
    image: Option[String],
    noOfComments: Int,
)

object PostView {
  def fromPost(post: Post, noOfComments: Int): PostView = PostView(
    post.id,
    post.title,
    post.message,
    post.locationId,
    post.createdAt,
    post.image,
    noOfComments,
  )
}
