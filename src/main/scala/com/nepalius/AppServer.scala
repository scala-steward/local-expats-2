package com.nepalius

import com.nepalius.config.ServerConfig
import com.nepalius.post.api.PostRoutes
import zhttp.http.{Http, Method, Request, Response, *}
import zhttp.service.Server
import zio.*

final case class AppServer(
    serverConfig: ServerConfig,
    postRoutes: PostRoutes,
):
  val allRoutes: HttpApp[Any, Throwable] = postRoutes.routes

  def start: ZIO[Any, Throwable, Unit] =
    Server.start(serverConfig.port, allRoutes)

object AppServer:
  val layer: ZLayer[ServerConfig & PostRoutes, Nothing, AppServer] =
    ZLayer.fromFunction(AppServer.apply)
