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

object App extends ZIOAppDefault {

  override def run: Task[Unit] =
    ZIO
      .serviceWithZIO[AppServer](_.start)
      .provide(
        AppServer.layer,
        AppConfig.layer,
        PostRoutes.layer,
        PostRepoLive.layer >>> PostServiceLive.layer,
        DatabaseContext.layer,
      )
}
