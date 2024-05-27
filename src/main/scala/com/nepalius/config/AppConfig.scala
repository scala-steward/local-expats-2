package com.nepalius.config
import com.nepalius.auth.AuthConfig
import zio.*
import zio.config.*
import zio.config.magnolia.*
import zio.config.typesafe.*

case class AppConfig(
    auth: AuthConfig,
    database: DatabaseConfig,
    server: ServerConfig,
)

case class ServerConfig(port: Int)

object AppConfig:
  private def readAppConfig: IO[Config.Error, AppConfig] =
    read(
      deriveConfig[AppConfig].from(
        TypesafeConfigProvider.fromResourcePath().nested("nepalius"),
      ),
    )

  val layer
      : ZLayer[Any, Config.Error, DatabaseConfig & ServerConfig & AuthConfig] =
    ZLayer.fromZIOEnvironment(
      readAppConfig.map(config =>
        ZEnvironment.empty
          .add(config.database)
          .add(config.server)
          .add(config.auth),
      ),
    )
