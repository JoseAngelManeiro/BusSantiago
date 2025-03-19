package org.galio.bussantiago.core.model

data class BusStop(
  val id: Int,
  val code: String,
  val name: String,
  val zone: String?,
  val extraordinary: Boolean,
  val coordinates: Coordinates
)
