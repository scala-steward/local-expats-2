package com.nepalius

import com.nepalius.post.api.PostRoutes
import zhttp.http.{Http, Method, Request, Response, *}
import zhttp.service.Server
import zio.*

class AppServer(postRoutes: PostRoutes) {

  val allRoutes: HttpApp[Any, Throwable] = postRoutes.routes

  def start: ZIO[Any, Throwable, Unit] =
    Server.start(9000, allRoutes)

}

object AppServer {
  val layer: ZLayer[PostRoutes, Nothing, AppServer] =
    ZLayer.fromFunction(AppServer(_))
}
