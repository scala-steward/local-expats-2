package com.nepalius.config

import com.typesafe.config.ConfigFactory
import io.getquill.jdbczio.Quill
import zio.{ZIO, ZLayer}

import javax.sql.DataSource
import scala.jdk.CollectionConverters.MapHasAsJava

object DataSourceContext:
  val live: ZLayer[DatabaseConfig, Throwable, DataSource] =
    ZLayer {
      for config <- ZIO.service[DatabaseConfig]
      yield Quill.DataSource.fromConfig(
        ConfigFactory.parseMap(
          DatabaseConfig.parseConnectionInfo(config.url).toMap.asJava,
        ),
      )
    }.flatten
