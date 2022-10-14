package com.nepalius.location.domain

import com.nepalius.location.State

case class Location(state: State, city: Option[String])
