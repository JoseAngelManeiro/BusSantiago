package org.galio.bussantiago.core.model

data class Route(
  val name: String,
  val direction: String,
  val busStops: List<BusStop>
)
