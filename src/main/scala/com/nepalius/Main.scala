package com.nepalius

import com.nepalius.config.{AppConfig, DatabaseContext, DatabaseMigration}
import com.nepalius.location.domain.LocationServiceLive
import com.nepalius.location.{LocationRepoLive, LocationRoutes}
import com.nepalius.post.api.PostRoutes
import com.nepalius.post.domain.PostServiceLive
import com.nepalius.post.repo.PostRepoLive
import zhttp.http.*
import zio.*
import zio.logging.backend.SLF4J

import java.io.IOException

object Main extends ZIOAppDefault {

  override def run: Task[Unit] =
    ZIO
      .serviceWithZIO[Server](_.start)
      .provide(
        Server.layer,
        AppConfig.layer,
        PostRoutes.layer,
        LocationRoutes.layer,
        PostRepoLive.layer >>> PostServiceLive.layer,
        LocationRepoLive.layer >>> LocationServiceLive.layer,
        DatabaseMigration.layer,
        DatabaseContext.layer,
        Runtime.removeDefaultLoggers >>> SLF4J.slf4j,
      )
}
