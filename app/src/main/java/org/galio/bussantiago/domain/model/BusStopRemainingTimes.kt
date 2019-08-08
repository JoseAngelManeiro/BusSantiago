package org.galio.bussantiago.domain.model

data class BusStopRemainingTimes(
  val id: Int,
  val code: String,
  val name: String,
  val zone: String?,
  val coordinates: Coordinates,
  val lineRemainingTimes: List<LineRemainingTime>
)
