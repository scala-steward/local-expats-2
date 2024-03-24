package com.nepalius.ui

import com.raquo.laminar.api.L.*

def LocationSelect(): Element =
  div(
    FetchStream.get("api/locations") --> { responseText =>
      println(responseText)
    },
  )
