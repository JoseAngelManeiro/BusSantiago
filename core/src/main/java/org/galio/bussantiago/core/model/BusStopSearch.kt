package org.galio.bussantiago.core.model

data class BusStopSearch(
  val id: Int,
  val code: String,
  val name: String,
  val zone: String?,
  val coordinates: Coordinates,
  val lines: List<LineSearch>
) {
  override fun toString(): String {
    return "$code $name"
  }
}
