package com.nepalius

import com.nepalius.post.api.PostRoutes
import zhttp.http.*
import zio.*
import zio.UIO
import zio.Console.*

import java.io.IOException

object App extends ZIOAppDefault {

  override def run: Task[Unit] = ZIO
    .serviceWithZIO[AppServer](_.start)
    .provide(
      AppServer.layer,
      PostRoutes.layer,
    )
}
