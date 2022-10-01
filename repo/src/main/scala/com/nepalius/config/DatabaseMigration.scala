package com.nepalius.config

import javax.sql.DataSource

import zio.*
import org.flywaydb.core.Flyway

case class DatabaseMigration(dataSource: DataSource):
  val migrate: Task[Unit] = ZIO.attempt {
    Flyway
      .configure()
      .dataSource(dataSource)
      .load()
      .migrate()
  }

object DatabaseMigration:
  val layer: ZLayer[DataSource, Nothing, DatabaseMigration] =
    ZLayer.fromFunction(DatabaseMigration.apply)
