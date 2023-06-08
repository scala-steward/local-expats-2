package com.nepalius.location

import com.nepalius.location.State
import com.nepalius.location.domain.LocationService
import com.nepalius.post.api.PostDto
import com.nepalius.post.domain.Post.PostId
import com.nepalius.post.domain.{Post, PostService}
import zio.*
import zio.http.*
import zio.http.Method.GET
import zio.json.*

final case class LocationRoutes(locationService: LocationService):
  val routes: Http[Any, Throwable, Request, Response] =
    Http.collectZIO[Request] { case GET -> Root / "api" / "locations" => getAll }

  private def getAll =
    for
      locations <- locationService.getAll
      dtos = locations.map(LocationDto.make)
    yield Response.json(dtos.toJson)

object LocationRoutes:
  val layer = ZLayer.fromFunction(LocationRoutes.apply)
