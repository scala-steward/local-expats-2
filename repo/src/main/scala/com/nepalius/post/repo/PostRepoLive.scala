package com.nepalius.post.repo
import com.nepalius.config.DatabaseContext.*
import com.nepalius.location.StateDbCodec.given
import com.nepalius.post.domain.{CreatePost, Post, PostRepo}

import java.sql.SQLException
import java.time.LocalDateTime
import java.util.UUID
import javax.sql.DataSource
import io.getquill.*
import zio.*

final case class PostRepoLive(dataSource: DataSource) extends PostRepo:
  override def getAll: ZIO[Any, SQLException, List[Post]] =
    run(query[Post])
      .provideEnvironment(ZEnvironment(dataSource))

  override def create(post: CreatePost): ZIO[Any, SQLException, Post] =
    run {
      query[Post]
        .insert(
          _.message -> lift(post.message),
          _.targetState -> lift(post.targetState),
          _.targetZipCode -> lift(post.targetZipCode),
        )
        .returningGenerated(p => (p.id, p.createdAt))
    }
      .provideEnvironment(ZEnvironment(dataSource))
      .map((id, createdAt) =>
        Post(id, post.message, post.targetState, post.targetZipCode, createdAt),
      )

object PostRepoLive:
  val layer: ZLayer[DataSource, Nothing, PostRepo] =
    ZLayer.fromFunction(PostRepoLive.apply)
