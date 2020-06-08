package org.galio.bussantiago.domain.model

data class BusStopSearch(
  val id: Int,
  val code: String,
  val name: String,
  val zone: String?,
  val coordinates: Coordinates,
  val lines: List<LineSearch>
)
