package com.nepalius.post.repo
import com.nepalius.config.QuillContext
import com.nepalius.location.domain.Location
import com.nepalius.location.domain.Location.LocationId
import com.nepalius.post.domain.*
import com.nepalius.post.domain.Post.PostId
import com.nepalius.util.Pageable
import io.getquill.*
import io.getquill.extras.*
import io.getquill.jdbczio.Quill
import zio.*

import java.sql.SQLException
import java.time.ZonedDateTime

case class PostRepoLive(
    quill: QuillContext,
) extends PostRepo:
  import quill.*

  override def getOne(
      id: PostId,
  ): ZIO[Any, SQLException, Option[PostWithComments]] =
    run {
      for
        post <- query[Post].filter(_.id == lift(id))
        comment <- query[Comment].leftJoin(_.postId == post.id)
      yield (post, comment)
    }
      .map(convertToPostWithComments)

  override def getAll(
      pageable: Pageable,
      locationId: LocationId,
  ): Task[List[PostView]] =
    run {
      quote {
        sql"""SELECT post.id, post.title, post.message, post.location_id, post.created_at, post.image, COUNT(comment.id) AS no_of_comments
                FROM post
           LEFT JOIN comment on post.id = comment.post_id
                JOIN location post_location
                  ON post.location_id = post_location.id
                JOIN location filter_location
                  ON filter_location.id = ${lift(locationId)}
                 AND (filter_location.city IS NULL OR filter_location.id = post_location.id)
                 AND (filter_location.state IS NULL OR filter_location.state = post_location.state)
               WHERE post.id < ${lift(pageable.lastId)}
            GROUP BY post.id
               ORDER BY post.id DESC
               LIMIT ${lift(pageable.pageSize)}
           """.as[Query[PostView]]
      }
    }

  override def getUpdated(
      ids: List[PostId],
      since: ZonedDateTime,
  ): Task[List[PostView]] = {
    if ids.isEmpty
    then ZIO.succeed(List())
    else {
      run {
        quote {
          sql"""SELECT post.id, post.title, post.message, post.location_id, post.created_at, post.image, COUNT(comment.id) AS no_of_comments
                  FROM post
                  JOIN comment ON post.id = comment.post_id
                 WHERE comment.created_at > ${lift(since)}
              GROUP BY post.id
           """.as[Query[PostView]]
        }
          .filter(p => lift(ids).contains(p.id)) // Fix nested query
      }
    }
  }

  override def create(post: CreatePost): ZIO[Any, SQLException, Post] =
    run {
      query[Post]
        .insert(
          _.title -> lift(post.title),
          _.message -> lift(post.message),
          _.locationId -> lift(post.locationId),
          _.image -> lift(post.image),
        )
        .returningGenerated(p => (p.id, p.createdAt))
    }
      .map((id, createdAt) =>
        Post(
          id,
          post.title,
          post.message,
          post.locationId,
          createdAt,
          post.image,
        ),
      )

  override def addComment(
      postId: PostId,
      comment: CreateComment,
  ): Task[Comment] =
    run {
      query[Comment]
        .insert(
          _.postId -> lift(postId),
          _.message -> lift(comment.message),
          _.image -> lift(comment.image),
        )
        .returningGenerated(c => (c.id, c.createdAt))
    }
      .map((id, createdAt) =>
        Comment(
          id,
          postId,
          comment.message,
          comment.image,
          createdAt,
        ),
      )

  private def convertToPostWithComments(
      postWithCommentRows: List[(Post, Option[Comment])],
  ): Option[PostWithComments] = {
    val post = postWithCommentRows.headOption.map(_._1)
    val comments = postWithCommentRows.flatMap(_._2.toList)
    post.map(PostWithComments(_, comments))
  }

object PostRepoLive:
  // noinspection TypeAnnotation
  val layer = ZLayer.fromFunction(PostRepoLive.apply)
