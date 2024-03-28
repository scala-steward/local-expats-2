package com.nepalius.ui

import com.raquo.laminar.api.L.*

def App(): Element =
  div(
    h1("NepaliUS"),
    LocationSelect(),
  )
