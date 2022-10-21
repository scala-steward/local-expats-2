package com.nepalius.post.repo
import com.nepalius.config.DatabaseContext.*
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

final case class PostRepoLive(dataSource: DataSource) extends PostRepo:

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
  ): ZIO[Any, SQLException, List[Post]] =
    run {
      query[Post]
        .join(query[Location])
        .on(_.locationId == _.id)
        .join(query[Location])
        .on({ case ((_, postLocation), filterLocation) =>
          filterLocation.id == lift(locationId)
          && (filterLocation.city.isEmpty || filterLocation.city == postLocation.city)
          && (filterLocation.state.isEmpty || filterLocation.state == postLocation.state)
        })
        .map({ case ((post, _), _) => post })
        .filter(_.id < lift(pageable.lastId))
        .sortBy(_.id)(Ord.desc)
        .take(lift(pageable.pageSize))
    }
      .provideEnvironment(ZEnvironment(dataSource))

  override def getUpdated(
      ids: List[PostId],
      since: ZonedDateTime,
  ): ZIO[Any, SQLException, List[Post]] =
    run {
      query[Post]
        .join(query[Comment])
        .on(_.id == _.postId)
        // ZonedDateTime comparison isn't working
// .filter({ case (_, comment) => comment.createdAt > lift(since) })
        .map(_._1)
        .filter(post => liftQuery(ids).contains(post.id))
        .distinct
    }
      .provideEnvironment(ZEnvironment(dataSource))

  override def create(post: CreatePost): ZIO[Any, SQLException, Post] =
    run {
      query[Post]
        .insert(
          _.title -> lift(post.title),
          _.message -> lift(post.message),
          _.locationId -> lift(post.locationId),
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
  val layer: ZLayer[DataSource, Nothing, PostRepo] =
    ZLayer.fromFunction(PostRepoLive.apply)
