package org.galio.bussantiago.features.stops.map

data class LineMapModel(
  val lineStyle: String,
  val busStopMapModels: List<BusStopMapModel>
)
