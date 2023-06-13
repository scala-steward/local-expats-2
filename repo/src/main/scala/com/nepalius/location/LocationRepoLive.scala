package com.nepalius.location
import com.nepalius.config.QuillContext
import com.nepalius.location.State
import com.nepalius.location.StateDbCodec.given
import com.nepalius.location.domain.{Location, LocationRepo}
import io.getquill.*
import io.getquill.Ord.{asc, ascNullsFirst}
import io.getquill.jdbczio.Quill
import zio.*

import javax.sql.DataSource

class LocationRepoLive(quill: QuillContext) extends LocationRepo:
  import quill.*

  override def getAll: Task[List[Location]] =
    run {
      query[Location]
        .sortBy(l => (l.state, l.city))(Ord(asc, ascNullsFirst))
    }

object LocationRepoLive:
  val live: ZLayer[QuillContext, Nothing, LocationRepoLive] =
    ZLayer.fromFunction(new LocationRepoLive(_))
