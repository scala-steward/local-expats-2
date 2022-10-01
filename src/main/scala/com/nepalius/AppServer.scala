package com.nepalius

import com.nepalius.config.ServerConfig
import com.nepalius.post.api.PostRoutes
import com.nepalius.post.domain.PostRepo
import zhttp.http.{Http, Method, Request, Response, *}
import zhttp.service.Server
import zio.*
import com.nepalius.config.DatabaseMigration

final case class AppServer(
    serverConfig: ServerConfig,
    databaseMigration: DatabaseMigration,
    postRoutes: PostRoutes,
):
  val allRoutes: HttpApp[Any, Throwable] = postRoutes.routes

  def start: ZIO[Any, Throwable, Unit] =
    for
      _ <- databaseMigration.migrate
      _ <- Server.start(serverConfig.port, allRoutes)
    yield ()

object AppServer:
  val layer: ZLayer[
    ServerConfig & DatabaseMigration & PostRoutes,
    Nothing,
    AppServer,
  ] =
    ZLayer.fromFunction(AppServer.apply)
