package com.nepalius.common.api

import sttp.tapir.ztapir.ZServerEndpoint

trait BaseApi:
  def endpoints: List[ZServerEndpoint[Any, Any]]
