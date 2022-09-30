package com.nepalius.config

import com.typesafe.config.ConfigFactory
import io.getquill.PostgresZioJdbcContext.apply
import io.getquill.SnakeCase
import zio.ZLayer
import io.getquill.context.ZioJdbc.DataSourceLayer
import io.getquill.PostgresZioJdbcContext

import scala.jdk.CollectionConverters.MapHasAsJava
import io.getquill.jdbczio.Quill

import javax.sql.DataSource
import scala.language.adhocExtensions

object QuillContext extends PostgresZioJdbcContext(SnakeCase) {
  val dataSourceLayer: ZLayer[Any, Throwable, DataSource] =
    Quill.DataSource.fromConfig(
      ConfigFactory.parseMap(
        Map(
          "dataSource.user" -> "postgres",
          "dataSource.password" -> "postgres",
          "dataSource.url" -> "jdbc:postgresql://localhost:5432/nepalius",
          "dataSourceClassName" -> "org.postgresql.ds.PGSimpleDataSource",
        ).asJava,
      ),
    )
}
