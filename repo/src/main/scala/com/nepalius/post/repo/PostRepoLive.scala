package com.nepalius.post.repo
import com.nepalius.config.DatabaseContext.*
import com.nepalius.util.Pageable
import com.nepalius.location.StateDbCodec.given
import com.nepalius.post.domain.Post.PostId
import com.nepalius.post.domain.{CreatePost, Post, PostRepo}

import java.sql.SQLException
import java.time.LocalDateTime
import java.util.UUID
import javax.sql.DataSource
import io.getquill.*
import zio.*

final case class PostRepoLive(dataSource: DataSource) extends PostRepo:

  override def getOne(id: PostId): ZIO[Any, SQLException, Option[Post]] =
    run {
      query[Post]
        .filter(_.id == lift(id))
    }
      .provideEnvironment(ZEnvironment(dataSource))
      .map(_.headOption)

  override def getAll(pageable: Pageable): ZIO[Any, SQLException, List[Post]] =
    run {
      query[Post]
        .sortBy(_.id)(Ord.desc)
        .take(lift(pageable.pageSize))
        .filter(p => lift(pageable.lastId) > p.id)
    }
      .provideEnvironment(ZEnvironment(dataSource))

  override def create(post: CreatePost): ZIO[Any, SQLException, Post] =
    run {
      query[Post]
        .insert(
          _.title -> lift(post.title),
          _.message -> lift(post.message),
          _.state -> lift(post.state),
        )
        .returningGenerated(p => (p.id, p.createdAt))
    }
      .provideEnvironment(ZEnvironment(dataSource))
      .map((id, createdAt) =>
        Post(
          id,
          post.title,
          post.message,
          post.state,
          createdAt,
        ),
      )

object PostRepoLive:
  val layer: ZLayer[DataSource, Nothing, PostRepo] =
    ZLayer.fromFunction(PostRepoLive.apply)
