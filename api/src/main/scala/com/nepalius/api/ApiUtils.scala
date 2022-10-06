package com.nepalius.api

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
