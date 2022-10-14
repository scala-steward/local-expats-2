package com.nepalius.location
import com.nepalius.location.domain.{Location, LocationRepo}
import zio.Task

import javax.sql.DataSource
import com.nepalius.config.DatabaseContext.*
import io.getquill.*
import zio.*
import com.nepalius.location.State
import com.nepalius.location.StateDbCodec.given

final case class LocationRepoLive(dataSource: DataSource) extends LocationRepo:
  override def getAll: Task[List[Location]] =
    run(query[Location])
      .provideEnvironment(ZEnvironment(dataSource))

object LocationRepoLive:
  val layer = ZLayer.fromFunction(LocationRepoLive.apply)
