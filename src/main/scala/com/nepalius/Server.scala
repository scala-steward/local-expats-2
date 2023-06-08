package com.nepalius

import com.nepalius.config.{DatabaseMigration, ServerConfig}
import com.nepalius.location.LocationRoutes
import com.nepalius.post.api.PostRoutes
import com.nepalius.post.domain.PostRepo
import zio.*
import zio.http.{Server as HttpServer, *}

final case class Server(
    serverConfig: ServerConfig,
    databaseMigration: DatabaseMigration,
    postRoutes: PostRoutes,
    locationRoutes: LocationRoutes,
):
  private val allRoutes: HttpApp[Any, Throwable] =
    postRoutes.routes ++ locationRoutes.routes

  def start: ZIO[Any, Throwable, Unit] =
    for
      _ <- databaseMigration.migrate()
      _ <- HttpServer
        .serve(allRoutes.withDefaultErrorResponse)
        .provide(HttpServer.defaultWithPort(serverConfig.port))
    yield ()

object Server:
  val layer = ZLayer.fromFunction(Server.apply)
