package com.nepalius.post.api

import zhttp.http.*
import zio.*
import zio.json.*

case class Post(id: String, message: String)

object Post {
  given JsonCodec[Post] = DeriveJsonCodec.gen[Post]
}

class PostRoutes() {

  val routes: Http[Any, Throwable, Request, Response] =
    Http.collectZIO[Request] { case Method.GET -> !! / "posts" =>
      ZIO.succeed(Response.json(List(Post("3", "Three")).toJson))
    }
}

object PostRoutes {
  val layer: ZLayer[Any, Nothing, PostRoutes] =
    ZLayer.fromFunction(() => PostRoutes())
}
