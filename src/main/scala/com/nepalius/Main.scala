package com.nepalius

import com.nepalius.common.api.BaseEndpoints
import com.nepalius.config.{AppConfig, DataSourceContext, DatabaseMigration, DoobieContext}
import com.nepalius.location.domain.LocationServiceLive
import com.nepalius.location.{LocationRepoLive, LocationRoutes}
import com.nepalius.post.api.PostRoutes
import com.nepalius.post.domain.PostServiceLive
import com.nepalius.post.repo.PostRepoLive
import com.nepalius.user.api.{UserEndpoints, UserServerEndpoints}
import com.nepalius.user.domain.UserService
import com.nepalius.user.repo.UserRepoLive
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
        BaseEndpoints.live,
        Endpoints.live,
        UserEndpoints.live,
        UserServerEndpoints.live,
        PostRoutes.layer,
        LocationRoutes.layer,
        PostRepoLive.layer >>> PostServiceLive.layer,
        LocationRepoLive.layer >>> LocationServiceLive.layer,
        UserRepoLive.live >>> UserService.live,
        DatabaseMigration.layer,
        DataSourceContext.layer,
        DoobieContext.liveTransactor,
        Runtime.removeDefaultLoggers >>> SLF4J.slf4j,
      )
}
