package com.nepalius.ui

import com.raquo.laminar.api.L.*
import tailwind.*

def Navbar(): HtmlElement =
  div(
    tw.h_8.bg_blue_500,
    div(
      tw.max_w_lg.mx_auto
        .h_full
        .flex.justify_between.items_center,
      a(tw.float_left, href := "/", "NepaliUS"),
      LocationSelect(),
      div(
        tw.float_right.flex.justify_between,
        a(tw.px_2, href := "/new", "New"),
        a(tw.px_2, href := "/login", "Login"),
      ),
    ),
  )
