package com.nepalius.location

import zio.json.*

object StateJsonCodec:
  given JsonEncoder[State] =
    JsonEncoder[String].contramap(_.toString)

  given JsonDecoder[State] =
    JsonDecoder[String].map(State.valueOf)
