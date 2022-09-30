package com.nepalius.config
import zio.*
import zio.config.*
import zio.config.magnolia.descriptor
import zio.config.typesafe.TypesafeConfigSource

final case class AppConfig(server: ServerConfig, db: DatabaseConfig)

final case class ServerConfig(host: String, port: Int)

object AppConfig:

  val databaseConfigLayer: ZLayer[Any, ReadError[String], DatabaseConfig] =
    ZLayer {
      read {
        descriptor[DatabaseConfig].from(
          TypesafeConfigSource.fromResourcePath.at(
            PropertyTreePath.$("nepalius.db"),
          ),
        )
      }
    }

  val serverConfigLayer: ZLayer[Any, ReadError[String], ServerConfig] =
    ZLayer {
      read {
        descriptor[ServerConfig].from(
          TypesafeConfigSource.fromResourcePath.at(
            PropertyTreePath.$("nepalius.server"),
          ),
        )
      }
    }
