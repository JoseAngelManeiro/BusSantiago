package org.galio.bussantiago.domain.model

data class SearchBusStop(
  val id: Int,
  val code: String,
  val name: String,
  val zone: String?,
  val coordinates: Coordinates,
  val lines: List<SearchLine>
)
