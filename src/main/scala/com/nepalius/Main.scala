package com.nepalius

import com.nepalius.auth.AuthService
import com.nepalius.common.api.BaseEndpoints
import com.nepalius.config.*
import com.nepalius.location.domain.LocationServiceLive
import com.nepalius.location.LocationRepoLive
import com.nepalius.location.api.{LocationEndpoints, LocationServerEndpoints}
import com.nepalius.post.api.{PostEndpoints, PostServerEndpoints}
import com.nepalius.post.domain.PostServiceLive
import com.nepalius.post.repo.PostRepoLive
import com.nepalius.user.api.{UserEndpoints, UserServerEndpoints}
import com.nepalius.user.domain.UserService
import com.nepalius.user.repo.UserRepoLive
import zio.*
import zio.logging.backend.SLF4J

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
        QuillContext.layer,
        // API
        AuthService.layer, 
        BaseEndpoints.layer,
        Endpoints.layer,
        UserEndpoints.layer,
        UserServerEndpoints.layer,
        PostEndpoints.layer,
        PostServerEndpoints.layer,
        LocationEndpoints.layer,
        LocationServerEndpoints.layer,
        // Service
        PostRepoLive.layer >>> PostServiceLive.layer,
        LocationRepoLive.layer >>> LocationServiceLive.layer,
        UserRepoLive.layer >>> UserService.layer,
      )
}
