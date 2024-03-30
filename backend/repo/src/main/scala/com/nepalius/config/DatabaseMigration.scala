package com.nepalius.config

import org.flywaydb.core.Flyway
import zio.*

import javax.sql.DataSource

case class DatabaseMigration(dataSource: DataSource):
  def migrate(): Task[Unit] = ZIO.attempt {
    Flyway
      .configure()
      .dataSource(dataSource)
      .load()
      .migrate()
    ()
  }

object DatabaseMigration:
  val layer = ZLayer.fromFunction(DatabaseMigration.apply)
