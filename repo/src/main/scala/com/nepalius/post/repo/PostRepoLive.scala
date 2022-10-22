package com.nepalius.post.repo
import com.nepalius.config.QuillContext.*
import com.nepalius.location.State
import com.nepalius.location.StateDbCodec.given
import com.nepalius.location.domain.Location
import com.nepalius.location.domain.Location.LocationId
import com.nepalius.post.domain.*
import com.nepalius.post.domain.Post.PostId
import com.nepalius.util.Pageable
import io.getquill.*
import io.getquill.extras.*
import zio.*

import java.sql.SQLException
import java.time.{LocalDateTime, ZonedDateTime}
import java.util.UUID
import javax.sql.DataSource
import doobie.util.transactor
import doobie.util.transactor.Transactor
import doobie.util.query.Query0
import doobie.implicits.*
import doobie.postgres.implicits.*
import doobie.util.query.Query0
import doobie.{Transactor, Update0}
import cats.data.OptionT
import cats.effect.MonadCancelThrow
import cats.implicits.*
import zio.interop.catz.*

final case class PostRepoLive(
    dataSource: DataSource,
    transactor: Transactor[Task],
) extends PostRepo:

  override def getOne(
      id: PostId,
  ): ZIO[Any, SQLException, Option[PostWithComments]] =
    run {
      for
        post <- query[Post].filter(_.id == lift(id))
        comment <- query[Comment].leftJoin(_.postId == post.id)
      yield (post, comment)
    }
      .provideEnvironment(ZEnvironment(dataSource))
      .map(convertToPostWithComments)

  override def getAll(
      pageable: Pageable,
      locationId: LocationId,
  ): Task[List[PostView]] =
    PostSql
      .getAll(pageable, locationId)
      .to[List]
      .transact(transactor)
      .foldZIO(err => ZIO.fail(err), posts => ZIO.succeed(posts))

  override def getUpdated(
      ids: List[PostId],
      since: ZonedDateTime,
  ): Task[List[PostView]] = {
    val idsNel = ids.toNel
    if idsNel.isEmpty
    then ZIO.succeed(List())
    else
      PostSql
        .getUpdated(idsNel.get, since)
        .to[List]
        .transact(transactor)
        .foldZIO(err => ZIO.fail(err), posts => ZIO.succeed(posts))
  }

  override def create(post: CreatePost): ZIO[Any, SQLException, Post] =
    run {
      query[Post]
        .insert(
          _.title -> lift(post.title),
          _.message -> lift(post.message),
          _.locationId -> lift(post.locationId),
//          _.images -> lift(post.images),
        )
        .returningGenerated(p => (p.id, p.createdAt))
    }
      .provideEnvironment(ZEnvironment(dataSource))
      .map((id, createdAt) =>
        Post(
          id,
          post.title,
          post.message,
          post.locationId,
          createdAt,
          List(),
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
        )
        .returningGenerated(c => (c.id, c.createdAt))
    }
      .provideEnvironment(ZEnvironment(dataSource))
      .map((id, createdAt) =>
        Comment(
          id,
          postId,
          comment.message,
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
  val layer = ZLayer.fromFunction(PostRepoLive.apply)

private object PostSql:

  import doobie.*
  import doobie.implicits.*
  import cats.*
  import cats.data.*
  import cats.effect.*
  import cats.implicits.*

  def getAll(pageable: Pageable, locationId: LocationId) =
    sql"""SELECT post.id, post.title, post.message, post.location_id, post.created_at, post.images, COUNT(comment.id) AS no_of_comments
            FROM post
       LEFT JOIN comment on post.id = comment.post_id
            JOIN location post_location
              ON post.location_id = post_location.id
            JOIN location filter_location
              ON filter_location.id = ${locationId}
             AND (filter_location.city IS NULL OR filter_location.city = post_location.city)
             AND (filter_location.state IS NULL OR filter_location.state = post_location.state)
           WHERE post.id < ${pageable.lastId}
        GROUP BY post.id
           ORDER BY post.id DESC
           LIMIT ${pageable.pageSize}
       """.query[PostView]

  def getUpdated(ids: NonEmptyList[PostId], since: ZonedDateTime) = {
    val q =
      fr"""SELECT post.id, post.title, post.message, post.location_id, post.created_at, post.images, COUNT(comment.id) AS no_of_comments
            FROM post
            JOIN comment ON post.id = comment.post_id
           WHERE comment.created_at > $since
             AND """ ++ Fragments.in(
        fr"post.id",
        ids,
      ) ++ fr"""GROUP BY post.id"""
    q.query[PostView]
  }
