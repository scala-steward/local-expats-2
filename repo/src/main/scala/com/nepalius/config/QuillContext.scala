package com.nepalius.config

import io.getquill.*
import io.getquill.jdbczio.*
import zio.ZLayer

import javax.sql.DataSource
import scala.language.adhocExtensions

object QuillContext:
  type QuillPostgres = Quill.Postgres[SnakeCase]
  val live: ZLayer[DataSource, Nothing, QuillPostgres] =
    Quill.Postgres.fromNamingStrategy(SnakeCase)
