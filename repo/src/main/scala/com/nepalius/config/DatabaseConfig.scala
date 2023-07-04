package com.nepalius.config

case class DatabaseConfig(
    url: String,
) {
  def toConnectionInfo = DatabaseConfig.parseConnectionInfo(url)
}

object DatabaseConfig {
  case class ConnectionInfo(
      username: String,
      password: String,
      host: String,
      port: String,
      dbname: String,
  ) {
    def jdbcUrl = s"jdbc:postgresql://$host:$port/$dbname"

    def toMap: Map[String, String] =
      Map(
        "jdbcUrl" -> jdbcUrl,
        "username" -> username,
        "password" -> password,
      )
  }

  def parseConnectionInfo(connectUri: String): ConnectionInfo = connectUri match
    case s"postgres://$username:$password@$host:$port/$dbname" =>
      ConnectionInfo(username, password, host, port, dbname)
}
