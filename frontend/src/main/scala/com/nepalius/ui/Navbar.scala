package com.nepalius.ui

import com.raquo.laminar.api.L.*
import tailwind.*

def Navbar(): HtmlElement =
  navTag(
    tw.h_full,
    a(tw.mx_4, href := "/", "NepaliUS"),
    LocationSelect(),
  )
