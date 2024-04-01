package com.nepalius.ui

import sttp.client3.FetchBackend
import sttp.tapir.client.sttp.SttpClientInterpreter

trait ApiClient:
  val backend = FetchBackend()
  val client = SttpClientInterpreter()
