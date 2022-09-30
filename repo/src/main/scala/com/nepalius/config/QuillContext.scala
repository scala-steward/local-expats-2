package com.nepalius.config

import com.typesafe.config.ConfigFactory
import io.getquill.PostgresZioJdbcContext.apply
import io.getquill.SnakeCase
import zio.*
import io.getquill.context.ZioJdbc.DataSourceLayer
import io.getquill.PostgresZioJdbcContext

import scala.jdk.CollectionConverters.MapHasAsJava
import io.getquill.jdbczio.Quill

import javax.sql.DataSource
import scala.language.adhocExtensions

object QuillContext extends PostgresZioJdbcContext(SnakeCase) {
  val dataSourceLayer: ZLayer[DatabaseConfig, Throwable, DataSource] =
    ZLayer {
      for config <- ZIO.service[DatabaseConfig]
      yield Quill.DataSource.fromConfig(
        ConfigFactory.parseMap(
          Map(
            "jdbcUrl" -> config.url,
            "username" -> config.user,
            "password" -> config.password,
          ).asJava,
        ),
      )
    }.flatten
}
