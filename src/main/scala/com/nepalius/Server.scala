package com.nepalius

import com.nepalius.config.{DatabaseMigration, ServerConfig}
import sttp.tapir.server.ziohttp.ZioHttpInterpreter
import zio.*
import zio.Console.printLine
import zio.http.{Server as HttpServer, *}

case class Server(
    serverConfig: ServerConfig,
    databaseMigration: DatabaseMigration,
    endpoints: Endpoints,
):

  private val allEndpoints = ZioHttpInterpreter().toHttp(endpoints.endpoints)

  def start: ZIO[Any, Throwable, Unit] =
    (for
      _ <- databaseMigration.migrate()
      port <- HttpServer.install(allEndpoints)
      _ <- printLine(s"Application NepaliUS started")
      _ <- printLine(s"Go to http://localhost:${port}/docs to open SwaggerUI")
      _ <- ZIO.never
    yield ())
      .provide(HttpServer.defaultWithPort(serverConfig.port))

object Server:
  // noinspection TypeAnnotation
  val layer = ZLayer.fromFunction(Server.apply)
