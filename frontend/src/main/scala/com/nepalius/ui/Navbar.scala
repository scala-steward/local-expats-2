package com.nepalius.ui

import com.raquo.laminar.api.L.*
import tailwind.*

def Navbar(): HtmlElement =
  navTag(
    tw.max_w_lg.mx_auto,
    a(tw.mx_4, href := "/", "NepaliUS"),
    LocationSelect(),
  )
