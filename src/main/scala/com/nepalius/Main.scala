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
        Server.layer,
        AppConfig.layer,
        Runtime.removeDefaultLoggers,
        SLF4J.slf4j,
        // Database
        DatabaseMigration.layer,
        DataSourceContext.layer,
        QuillContext.live,
        // API
        AuthService.live,
        BaseEndpoints.live,
        Endpoints.live,
        UserEndpoints.live,
        UserServerEndpoints.live,
        PostRoutes.layer,
        LocationRoutes.layer,
        // Service
        PostRepoLive.live >>> PostServiceLive.layer,
        LocationRepoLive.live >>> LocationServiceLive.layer,
        UserRepoLive.live >>> UserService.live,
      )
}
