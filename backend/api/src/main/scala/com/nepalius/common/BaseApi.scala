package com.nepalius.common

import sttp.tapir.ztapir.ZServerEndpoint

trait BaseApi:
  def endpoints: List[ZServerEndpoint[Any, Any]]
