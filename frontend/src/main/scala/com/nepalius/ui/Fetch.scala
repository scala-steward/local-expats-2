package com.nepalius.ui

import com.raquo.airstream.core.EventStream
import org.scalajs.macrotaskexecutor.MacrotaskExecutor.Implicits.*
import sttp.client3.*
import zio.json.*

object Fetch {

  private val backend = FetchBackend()

  def get[A: JsonCodec](path: Any*): EventStream[A] = {
    val request = quickRequest.get(getUri(path))
    EventStream.fromFuture(backend.send(request)).map { response =>
      response.body.fromJson[A] match {
        case Right(b) => b
        case Left(e)  => throw new Error(s"Error parsing JSON: $e")
      }
    }
  }

  private def getUri[A: JsonCodec](path: Any) = uri"/api/$path"
  
}
