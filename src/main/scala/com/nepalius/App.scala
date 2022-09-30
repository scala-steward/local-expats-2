package com.nepalius

import com.nepalius.post.api.PostRoutes
import zhttp.http.*
import zio.*
import zio.UIO
import zio.Console.*

import java.io.IOException
import com.nepalius.config.{AppConfig, QuillContext}
import com.nepalius.post.domain.PostServiceLive
import com.nepalius.post.repo.PostRepoLive

object App extends ZIOAppDefault {

  override def run: Task[Unit] = ZIO
    .serviceWithZIO[AppServer](_.start)
    .provide(
      AppServer.layer,
      PostRoutes.layer,
      AppConfig.databaseConfigLayer,
      AppConfig.serverConfigLayer,
      QuillContext.dataSourceLayer,
      PostServiceLive.layer,
      PostRepoLive.layer,
    )
}
