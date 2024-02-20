ThisBuild / organization := "com.nepalius"
ThisBuild / scalaVersion := "3.3.1"
ThisBuild / version := "0.0.1-SNAPSHOT"

Global / onChangedBuildSource := ReloadOnSourceChanges

val V = new {
  val CommonsValidator = "1.8.0"
  val Flyway = "10.8.1"
  val Jwt = "4.4.0"
  val Laminar = "16.0.0"
  val Password4J = "1.7.3"
  val Postgres = "42.7.1"
  val Quill = "4.8.1"
  val ScalaJsDom = "2.8.0"
  val Slf4j = "2.0.12"
  val Tapir = "1.9.9"
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

import org.scalajs.linker.interface.ModuleSplitStyle

lazy val client = project
  .enablePlugins(ScalaJSPlugin)
  .settings(
    scalaJSUseMainModuleInitializer := true,
    scalaJSLinkerConfig ~= {
      _.withModuleKind(ModuleKind.ESModule)
        .withModuleSplitStyle(
          ModuleSplitStyle.SmallModulesFor(List("com.nepalius.ui")),
        )
    },
  )
  .settings(
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % V.ScalaJsDom,
      "com.raquo" %%% "laminar" % V.Laminar,
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
      "com.github.ghik" % "silencer-lib_2.13.11" % "1.17.13" % "provided", // Fix for zio-config incompatibility with scala 3.3
      "dev.zio" %% "zio-logging-slf4j" % V.ZioLogging,
      "org.slf4j" % "slf4j-api" % V.Slf4j,
      "org.slf4j" % "slf4j-simple" % V.Slf4j,
    ),
  )
  .enablePlugins(JavaAppPackaging)
  .enablePlugins(DockerPlugin)
  .settings(
    Docker / packageName := "nepalius",
    dockerBaseImage := "eclipse-temurin:17",
    dockerExposedPorts := Seq(9000),
    dockerUpdateLatest := true,
  )
