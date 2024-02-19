package com.nepalius.config

import io.getquill.*
import io.getquill.jdbczio.*
import zio.ZLayer

import javax.sql.DataSource
import scala.language.adhocExtensions

type QuillContext = Quill.Postgres[SnakeCase]

object QuillContext:
  val layer: ZLayer[DataSource, Nothing, QuillContext] =
    Quill.Postgres.fromNamingStrategy(SnakeCase)
