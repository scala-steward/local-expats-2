package com.nepalius

import com.nepalius.auth.AuthService
import com.nepalius.common.api.BaseEndpoints
import com.nepalius.config.*
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
        Server.live,
        AppConfig.live,
        Runtime.removeDefaultLoggers,
        SLF4J.slf4j,
        // Database
        DatabaseMigration.live,
        DataSourceContext.live,
        QuillContext.live,
        // API
        AuthService.live,
        BaseEndpoints.live,
        Endpoints.live,
        UserEndpoints.live,
        UserServerEndpoints.live,
        PostRoutes.live,
        LocationRoutes.live,
        // Service
        PostRepoLive.live >>> PostServiceLive.live,
        LocationRepoLive.live >>> LocationServiceLive.live,
        UserRepoLive.live >>> UserService.live,
      )
}
