package org.galio.bussantiago.features.stops.map

import org.galio.bussantiago.domain.model.Coordinates

data class BusStopMapModel(
  val code: String,
  val name: String,
  val coordinates: Coordinates
)
