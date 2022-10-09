package com.nepalius.util

import com.nepalius.util.Pageable
import zhttp.http.Request
import zio.json.*
import zio.*

object ApiUtils:

  def parseBody[A: JsonDecoder](request: Request): IO[AppError, A] =
    for
      body <- request.body.asString.orElseFail(AppError.MissingBodyError)
      parsed <- ZIO
        .from(body.fromJson[A])
        .mapError(AppError.JsonDecodingError.apply)
    yield parsed

  def parsePageable(req: Request): Pageable = {
    val params = req.url.queryParams
    val maxPageSize = 100
    Pageable(
      params
        .get("pageSize")
        .flatMap(_.headOption)
        .map(_.toInt)
        .getOrElse(maxPageSize)
        .min(maxPageSize),
      params
        .get("lastId")
        .flatMap(_.headOption)
        .map(_.toLong)
        .getOrElse(Long.MaxValue),
    )
  }
