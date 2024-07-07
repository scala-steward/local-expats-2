package com.nepalius.ui

import com.raquo.laminar.api.L.*
import tailwind.*

def Navbar(): HtmlElement =
  navTag(
    tw.max_w_3xl.mx_auto,
    a(tw.mx_4, href := "/", "NepaliUS"),
    LocationSelect(),
  )
