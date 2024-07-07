package com.nepalius.ui

import com.raquo.laminar.api.L.*
import tailwind.*

def Navbar(): HtmlElement =
  navTag(
    tw.h_8,
    div(
      tw.flex.justify_between.items_center.h_full,
      a(href := "/", "NepaliUS"),
      LocationSelect(),
      a(href := "/new", "New"),
      a(href := "/login", "Login"),
    ),
  )
