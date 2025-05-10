package com.nepalius.location
import com.nepalius.config.QuillContext
import com.nepalius.location.StateDbCodec.given
import io.getquill.*
import io.getquill.Ord.{asc, ascNullsFirst}
import zio.*

case class LocationRepoLive(quill: QuillContext) extends LocationRepo:
  import quill.*

  override def getAll: Task[List[Location]] =
    run {
      query[Location]
        .sortBy(l => (l.state, l.city))(using Ord(asc, ascNullsFirst))
    }

object LocationRepoLive:
  val layer = ZLayer.fromFunction(LocationRepoLive.apply)
