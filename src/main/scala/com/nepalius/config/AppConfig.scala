package com.nepalius.config
import com.nepalius.auth.AuthConfig
import zio.*
import zio.config.*
import zio.config.magnolia.descriptor
import zio.config.typesafe.TypesafeConfigSource

final case class AppConfig(
    auth: AuthConfig,
    database: DatabaseConfig,
    server: ServerConfig,
)

final case class ServerConfig(port: Int)

object AppConfig:
  private def readAppConfig: IO[ReadError[String], AppConfig] = read {
    descriptor[AppConfig].from(
      TypesafeConfigSource.fromResourcePath.at(
        PropertyTreePath.$("nepalius"),
      ),
    )
  }

  val live: ZLayer[Any, ReadError[String], DatabaseConfig & ServerConfig & AuthConfig] =
    ZLayer {
      for appConfig <- readAppConfig
      yield ZLayer.succeed(appConfig.database) ++ ZLayer.succeed(appConfig.server) ++ ZLayer.succeed(appConfig.auth)
    }.flatten
