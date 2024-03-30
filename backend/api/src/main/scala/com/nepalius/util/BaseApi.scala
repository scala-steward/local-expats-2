package com.nepalius.util

import sttp.tapir.ztapir.ZServerEndpoint

trait BaseApi:
  def endpoints: List[ZServerEndpoint[Any, Any]]
