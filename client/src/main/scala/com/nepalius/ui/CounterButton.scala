package com.nepalius.ui

import com.raquo.laminar.api.L.*

def CounterButton(): Element =
  val counter = Var(0)
  button(
    tpe := "button",
    cls := "bg-blue-500",
    "Count is ",
    child.text <-- counter,
    onClick --> { _ => counter.update(_ + 1) },
  )
end CounterButton
