package com.nepalius.ui

import com.raquo.laminar.api.L.renderOnDomContentLoaded
import org.scalajs.dom

@main
def UiApp(): Unit =
  renderOnDomContentLoaded(
    dom.document.getElementById("app"),
    App(),
  )
