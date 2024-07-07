package com.nepalius.ui

import com.raquo.laminar.api.L.*

def Posts(): HtmlElement =
  val posts$ = PostApiClient.getPosts

  div(
    children <-- posts$.map(_.map(post => div(post.title))),
  )
