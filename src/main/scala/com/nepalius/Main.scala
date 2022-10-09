package com.nepalius

import com.nepalius.post.api.PostRoutes
import zhttp.http.*
import zio.*
import zio.UIO
import zio.Console.*

import java.io.IOException
import com.nepalius.config.{AppConfig, DatabaseContext}
import com.nepalius.post.domain.PostServiceLive
import com.nepalius.post.repo.PostRepoLive
import com.nepalius.config.DatabaseMigration
import zio.logging.backend.SLF4J

object Main extends ZIOAppDefault {

  override def run: Task[Unit] =
    ZIO
      .serviceWithZIO[Server](_.start)
      .provide(
        Server.layer,
        AppConfig.layer,
        PostRoutes.layer,
        PostRepoLive.layer >>> PostServiceLive.layer,
        DatabaseMigration.layer,
        DatabaseContext.layer,
        Runtime.removeDefaultLoggers >>> SLF4J.slf4j,
      )
}
