package com.nepalius

import com.nepalius.config.{DatabaseMigration, ServerConfig}
import com.nepalius.location.LocationRoutes
import com.nepalius.post.api.PostRoutes
import com.nepalius.post.domain.PostRepo
import sttp.tapir.server.ziohttp.ZioHttpInterpreter
import zio.*
import zio.Console.printLine
import zio.http.{Server as HttpServer, *}

final case class Server(
    serverConfig: ServerConfig,
    databaseMigration: DatabaseMigration,
    postRoutes: PostRoutes,
    locationRoutes: LocationRoutes,
    endpoints: Endpoints,
):

  private val allEndpoints = ZioHttpInterpreter().toHttp(endpoints.endpoints)

  private val allRoutes: HttpApp[Any, Throwable] =
    postRoutes.routes ++ locationRoutes.routes ++ allEndpoints

  def start: ZIO[Any, Throwable, Unit] =
    (for
      _ <- databaseMigration.migrate()
      port <- HttpServer.install(allRoutes.withDefaultErrorResponse)
      _ <- printLine(s"Application NepaliUS started")
      _ <- printLine(s"Go to http://localhost:${port}/docs to open SwaggerUI")
      _ <- ZIO.never
    yield ())
      .provide(HttpServer.defaultWithPort(serverConfig.port))

object Server:
  // noinspection TypeAnnotation
  val live = ZLayer.fromFunction(Server.apply)
