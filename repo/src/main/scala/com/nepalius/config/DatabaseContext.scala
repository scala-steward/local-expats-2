package com.nepalius.config

import com.typesafe.config.ConfigFactory
import io.getquill.PostgresZioJdbcContext.apply
import io.getquill.context.ZioJdbc.DataSourceLayer
import io.getquill.jdbczio.Quill
import io.getquill.{PostgresZioJdbcContext, SnakeCase}
import zio.*

import javax.sql.DataSource
import scala.jdk.CollectionConverters.MapHasAsJava
import scala.language.adhocExtensions

object DatabaseContext extends PostgresZioJdbcContext(SnakeCase) {
  val layer: ZLayer[DatabaseConfig, Throwable, DataSource] =
    ZLayer {
      for config <- ZIO.service[DatabaseConfig]
      yield Quill.DataSource.fromConfig(
        ConfigFactory.parseMap(
          parseConnectionInfo(config.url).toMap.asJava,
        ),
      )
    }.flatten

  final case class ConnectionInfo(
      username: String,
      password: String,
      host: String,
      port: String,
      dbname: String,
  ) {

    def toMap: Map[String, String] =
      Map(
        "jdbcUrl" -> s"jdbc:postgresql://$host:$port/$dbname",
        "username" -> username,
        "password" -> password,
      )
  }

  def parseConnectionInfo(connectUri: String): ConnectionInfo = connectUri match
    case s"postgres://$username:$password@$host:$port/$dbname" =>
      ConnectionInfo(username, password, host, port, dbname)
}
