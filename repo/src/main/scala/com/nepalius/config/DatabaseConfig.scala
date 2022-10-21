package com.nepalius.config

final case class DatabaseConfig(
    url: String,
)

object DatabaseConfig {
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
