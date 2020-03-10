package org.galio.bussantiago.features.stops

data class LineMapModel(
  val lineStyle: String,
  val busStopMapModels: List<BusStopMapModel>
)
