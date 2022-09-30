package com.nepalius.config
import zio.*
import zio.config.*
import zio.config.magnolia.descriptor
import zio.config.typesafe.TypesafeConfigSource

final case class AppConfig(server: ServerConfig, db: DatabaseConfig)

final case class ServerConfig(host: String, port: Int)

object AppConfig:
  private val readAppConfig: IO[ReadError[String], AppConfig] = read {
    descriptor[AppConfig].from(
      TypesafeConfigSource.fromResourcePath.at(
        PropertyTreePath.$("nepalius"),
      ),
    )
  }

  val appConfigLayer
      : ZLayer[Any, ReadError[String], DatabaseConfig & ServerConfig] =
    ZLayer {
      for appConfig <- readAppConfig
      yield ZLayer.succeed(appConfig.db) ++ ZLayer.succeed(appConfig.server)
    }.flatten
