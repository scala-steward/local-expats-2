package com.nepalius.config

import com.typesafe.config.ConfigFactory
import io.getquill.PostgresZioJdbcContext.apply
import io.getquill.context.ZioJdbc.DataSourceLayer
import io.getquill.jdbczio.Quill
import io.getquill.{PostgresZioJdbcContext, SnakeCase}
import zio.*

import javax.sql.DataSource
import scala.concurrent.ExecutionContext
import scala.jdk.CollectionConverters.MapHasAsJava
import scala.language.adhocExtensions
import doobie.util.transactor.Transactor
import doobie.hikari.HikariTransactor.apply
import doobie.hikari.HikariTransactor
import zio.managed.ZManaged
import cats.syntax.validated

object DatabaseContext {
  object QuillContext extends PostgresZioJdbcContext(SnakeCase)

  val layer: ZLayer[DatabaseConfig, Throwable, DataSource] =
    ZLayer {
      for config <- ZIO.service[DatabaseConfig]
      yield Quill.DataSource.fromConfig(
        ConfigFactory.parseMap(
          DatabaseConfig.parseConnectionInfo(config.url).toMap.asJava,
        ),
      )
    }.flatten

  object DoobieContext {
    import doobie.*
    import doobie.implicits.*
    import zio.Task
    import zio.interop.catz.*
    import cats.implicits.catsStdInstancesForFuture
    import zio.managed.ZManaged
    import zio.managed.ZManagedZIOSyntax

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
}
