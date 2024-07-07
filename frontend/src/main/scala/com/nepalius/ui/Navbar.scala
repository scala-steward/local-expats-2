package com.nepalius.ui

import com.raquo.laminar.api.L.*

def Navbar(): HtmlElement =
  navTag(
    cls("h-8"),
    div(
      cls("flex justify-between items-center h-full"),
      a(href := "/", "NepaliUS"),
      LocationSelect(),
      a(href := "/new", "New"),
      a(href := "/login", "Login"),
    ),
  )
