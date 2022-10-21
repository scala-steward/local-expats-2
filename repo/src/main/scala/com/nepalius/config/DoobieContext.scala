package com.nepalius.config

import com.typesafe.config.ConfigFactory
import io.getquill.PostgresZioJdbcContext.apply
import io.getquill.context.ZioJdbc.DataSourceLayer
import io.getquill.jdbczio.Quill
import io.getquill.{PostgresZioJdbcContext, SnakeCase}
import zio.*
import zio.interop.catz.*
import doobie.util.transactor.Transactor
import doobie.hikari.HikariTransactor.apply
import doobie.hikari.HikariTransactor
import cats.*
import cats.data.*
import cats.effect.*
import cats.implicits.*
import doobie.*
import doobie.implicits.*

import javax.sql.DataSource
import scala.concurrent.ExecutionContext

object DoobieContext {
  given zioRuntime: zio.Runtime[Any] = zio.Runtime.default
  given dispatcher: cats.effect.std.Dispatcher[zio.Task] = zio.Unsafe.unsafe {
    unsafe =>
      {
        given Unsafe = unsafe
        zioRuntime.unsafe
          .run(
            cats.effect.std
              .Dispatcher[zio.Task]
              .allocated,
          )
          .getOrThrowFiberFailure()(unsafe)
          ._1
      }
  }

  def transactor: ZIO[DatabaseConfig & Scope, Throwable, Transactor[Task]] =
    for
      ec <- ZIO.executor.map(_.asExecutionContext)
      config <- ZIO.service[DatabaseConfig]
      info = config.toConnectionInfo
      xa <- HikariTransactor
        .newHikariTransactor[Task](
          "org.postgresql.Driver",
          info.jdbcUrl,
          info.username,
          info.password,
          ec, // await connection here
        )
        .toScopedZIO
    yield xa

  val liveTransactor: ZLayer[DatabaseConfig, Throwable, Transactor[Task]] =
    ZLayer.scoped(transactor)
}
