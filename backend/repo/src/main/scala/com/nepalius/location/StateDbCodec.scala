package com.nepalius.location

import io.getquill.MappedEncoding

import scala.language.adhocExtensions

object StateDbCodec:
  given MappedEncoding[State, String](_.toString)
  given MappedEncoding[String, State](State.valueOf)
