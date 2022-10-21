package com.nepalius.config

import com.typesafe.config.ConfigFactory
import io.getquill.PostgresZioJdbcContext.apply
import io.getquill.context.ZioJdbc.DataSourceLayer
import io.getquill.jdbczio.Quill
import io.getquill.{PostgresZioJdbcContext, SnakeCase}
import zio.*
import zio.interop.catz.*
import zio.managed.{ZManaged, ZManagedZIOSyntax}
import doobie.util.transactor.Transactor
import doobie.hikari.HikariTransactor.apply
import doobie.hikari.HikariTransactor

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

  def transactor: ZManaged[Any, Throwable, Transactor[Task]] =
    for {
      // rt <- ZIO.runtime[Any].toManaged
      ec <- ZIO.executor.map(_.asExecutionContext).toManaged
      xa <- HikariTransactor
        .newHikariTransactor[Task](
          "org.postgresql.Driver",
          "jdbc:postgresql://localhost:5432/nepalius",
          "postgres",
          "postgres",
          ec, // await connection here
        )
        .toManaged
    } yield xa

  val liveTransactor: ZLayer[Any, Throwable, Transactor[Task]] =
    transactor.toLayer
}
