package com.nepalius.ui

import com.raquo.laminar.api.L.*
import tailwind.*

def App(): Element =
  div(
    Navbar(),
    div(
      tw.max_w_3xl.mx_auto.px_2,
      Posts(),
    ),
  )
