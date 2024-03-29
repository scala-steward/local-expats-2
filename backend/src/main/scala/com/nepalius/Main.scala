package com.nepalius

import com.nepalius.auth.AuthService
import com.nepalius.config.*
import com.nepalius.location.domain.LocationServiceLive
import com.nepalius.location.LocationRepoLive
import com.nepalius.location.api.LocationApi
import com.nepalius.post.api.PostApi
import com.nepalius.post.domain.PostServiceLive
import com.nepalius.post.repo.PostRepoLive
import com.nepalius.user.api.UserApi
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
        Endpoints.layer,
        UserApi.layer,
        PostApi.layer,
        LocationApi.layer,
        // Service
        AuthService.layer,
        PostRepoLive.layer >>> PostServiceLive.layer,
        LocationRepoLive.layer >>> LocationServiceLive.layer,
        UserRepoLive.layer >>> UserService.layer,
      )
}
