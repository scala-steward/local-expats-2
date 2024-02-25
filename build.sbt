ThisBuild / organization := "com.nepalius"
ThisBuild / scalaVersion := "3.3.1"
ThisBuild / version := "0.0.1-SNAPSHOT"

Global / onChangedBuildSource := ReloadOnSourceChanges

val V = new {
  val CommonsValidator = "1.8.0"
  val Flyway = "10.8.1"
  val Jwt = "4.4.0"
  val Logback = "1.5.0"
  val Password4J = "1.7.3"
  val Postgres = "42.7.2"
  val Quill = "4.8.1"
  val Slf4j = "2.0.12"
  val Tapir = "1.9.10"
  val Zio = "2.0.21"
  val ZioConfig = "4.0.1"
  val ZioLogging = "2.2.2"
}

lazy val domain = project
  .settings(
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % V.Zio,
      "commons-validator" % "commons-validator" % V.CommonsValidator,
    ),
  )

lazy val repo = project
  .dependsOn(domain)
  .settings(
    libraryDependencies ++= Seq(
      "org.postgresql" % "postgresql" % V.Postgres,
      "org.flywaydb" % "flyway-core" % V.Flyway,
      "org.flywaydb" % "flyway-database-postgresql" % V.Flyway,
      "io.getquill" %% "quill-jdbc-zio" % V.Quill,
    ),
  )
  .enablePlugins(FlywayPlugin)
  .settings(
    flywayUrl := "jdbc:postgresql://localhost:5432/nepalius",
    flywayUser := "postgres",
    flywayPassword := "postgres",
  )

lazy val api = project
  .dependsOn(domain)
  .settings(
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.tapir" %% "tapir-zio-http-server" % V.Tapir,
      "com.softwaremill.sttp.tapir" %% "tapir-json-zio" % V.Tapir,
      "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % V.Tapir,
      "com.auth0" % "java-jwt" % V.Jwt,
      "com.password4j" % "password4j" % V.Password4J,
    ),
  )

lazy val root = (project in file("."))
  .settings(name := "NepaliUS")
  .aggregate(domain, repo, api)
  .dependsOn(domain, repo, api)
  .settings(reStart / aggregate := false)
  .settings(
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio-config" % V.ZioConfig,
      "dev.zio" %% "zio-config-typesafe" % V.ZioConfig,
      "dev.zio" %% "zio-config-magnolia" % V.ZioConfig,
      "dev.zio" %% "zio-logging-slf4j2" % V.ZioLogging,
      "ch.qos.logback" % "logback-classic" % V.Logback,
    ),
  )
  .enablePlugins(JavaAppPackaging)
  .enablePlugins(DockerPlugin)
  .settings(
    Docker / packageName := "nepalius",
    dockerBaseImage := "eclipse-temurin:21",
    dockerExposedPorts := Seq(9000),
    dockerUpdateLatest := true,
  )
