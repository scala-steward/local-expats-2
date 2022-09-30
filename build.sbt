ThisBuild / organization := "com.nepalius"
ThisBuild / scalaVersion := "3.2.0"
ThisBuild / version := "0.0.1-SNAPSHOT"

val V = new {
  val CatsEffect = "3.3.14"
  val CatsEffectTime = "0.2.0"
  val Circe = "0.14.3"
  val CirceConfig = "0.10.0"
  val Doobie = "1.0.0-RC2"
  val Flyway = "9.4.0"
  val Http4s = "0.23.16"
  val Jansi = "1.18"
  val Logback = "1.4.1"
  val Postgres = "42.5.0"
  val Quill = "4.5.0"
  val TSec = "0.4.0"
  val Zio = "2.0.2"
  val ZioHttp = "2.0.0-RC11"
  val ZioJson = "0.3.0"
}

lazy val domain = project
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      "ch.qos.logback" % "logback-classic" % V.Logback,
      "org.typelevel" %% "cats-effect" % V.CatsEffect,
      "io.chrisdavenport" %% "cats-effect-time" % V.CatsEffectTime,
      "dev.zio" %% "zio" % V.Zio,
    ),
  )

lazy val repo = project
  .dependsOn(domain)
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      "org.postgresql" % "postgresql" % V.Postgres,
      "org.tpolecat" %% "doobie-free" % V.Doobie,
      "org.tpolecat" %% "doobie-core" % V.Doobie,
      "org.tpolecat" %% "doobie-postgres" % V.Doobie,
      "org.tpolecat" %% "doobie-hikari" % V.Doobie,
      "org.flywaydb" % "flyway-core" % V.Flyway,
      "io.getquill" %% "quill-jdbc-zio" % V.Quill,
    ),
  )

lazy val api = project
  .dependsOn(domain)
  .settings(commonSettings)
  .settings(
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-generic" % V.Circe,
      "org.http4s" %% "http4s-ember-server" % V.Http4s,
      "org.http4s" %% "http4s-circe" % V.Http4s,
      "org.http4s" %% "http4s-dsl" % V.Http4s,
      "io.github.jmcardon" %% "tsec-common" % V.TSec,
      "io.github.jmcardon" %% "tsec-password" % V.TSec,
      "io.github.jmcardon" %% "tsec-http4s" % V.TSec,
      "io.d11" %% "zhttp" % V.ZioHttp,
      "dev.zio" %% "zio-json" % V.ZioJson,
    ),
  )

lazy val root = (project in file("."))
  .settings(name := "NepaliUS")
  .settings(commonSettings)
  .aggregate(domain, api, repo)
  .settings(reStart / aggregate := false)
  .settings(
    libraryDependencies ++= Seq(
      "com.hunorkovacs" %% "circe-config" % V.CirceConfig,
      "org.fusesource.jansi" % "jansi" % V.Jansi,
    ),
  )
  .dependsOn(domain, api, repo)

lazy val commonSettings = Seq(
  scalacOptions ++= Seq(
    "-new-syntax",
    "-source:future",
  ),
)
