package com.nepalius.config

import io.getquill.{PostgresZioJdbcContext, SnakeCase}

import scala.language.adhocExtensions

object QuillContext extends PostgresZioJdbcContext(SnakeCase)
