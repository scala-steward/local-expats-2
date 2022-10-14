package com.nepalius

import com.nepalius.config.{DatabaseMigration, ServerConfig}
import com.nepalius.location.LocationRoutes
import com.nepalius.post.api.PostRoutes
import com.nepalius.post.domain.PostRepo
import zhttp.http.*
import zhttp.service.Server as HttpServer
import zio.*

final case class Server(
    serverConfig: ServerConfig,
    databaseMigration: DatabaseMigration,
    postRoutes: PostRoutes,
    locationRoutes: LocationRoutes,
):
  val allRoutes: HttpApp[Any, Throwable] =
    postRoutes.routes ++ locationRoutes.routes

  def start: ZIO[Any, Throwable, Unit] =
    for
      _ <- databaseMigration.migrate()
      _ <- HttpServer.start(serverConfig.port, allRoutes)
    yield ()

object Server:
  val layer: ZLayer[
    ServerConfig & DatabaseMigration & PostRoutes & LocationRoutes,
    Nothing,
    Server,
  ] =
    ZLayer.fromFunction(Server.apply)
