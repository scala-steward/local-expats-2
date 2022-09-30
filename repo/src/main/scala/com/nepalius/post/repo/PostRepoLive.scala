package com.nepalius.post.repo
import com.nepalius.config.QuillContext
import com.nepalius.post.domain.{Post, PostRepo}
import io.getquill.*
import io.getquill.autoQuote
import zio.*

import java.sql.SQLException
import javax.sql.DataSource

class PostRepoLive(dataSource: DataSource) extends PostRepo:

  import QuillContext.*

  override def getAll: ZIO[Any, SQLException, List[Post]] =
    QuillContext
      .run(query[Post])
      .provideEnvironment(ZEnvironment(dataSource))

  inline def all: Quoted[EntityQuery[Post]] = quote {
    query[Post]
  }

object PostRepoLive:
  val layer: ZLayer[DataSource, Nothing, PostRepo] =
    ZLayer.fromFunction(PostRepoLive(_))
