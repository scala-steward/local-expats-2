package com.nepalius.post.repo
import com.nepalius.config.QuillContext.*
import com.nepalius.location.StateDbCodec.given
import com.nepalius.post.domain.{Post, PostRepo}
import io.getquill.*
import zio.*

import java.sql.SQLException
import javax.sql.DataSource

final case class PostRepoLive(dataSource: DataSource) extends PostRepo:
  override def getAll: ZIO[Any, SQLException, List[Post]] =
    run(query[Post])
      .provideEnvironment(ZEnvironment(dataSource))

object PostRepoLive:
  val layer: ZLayer[DataSource, Nothing, PostRepo] =
    ZLayer.fromFunction(PostRepoLive.apply)
