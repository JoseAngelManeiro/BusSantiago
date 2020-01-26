package org.galio.bussantiago.domain.model

data class Route(
  val name: String,
  val direction: String,
  val busStops: List<BusStop>
)
