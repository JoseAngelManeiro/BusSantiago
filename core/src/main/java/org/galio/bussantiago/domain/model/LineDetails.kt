package org.galio.bussantiago.domain.model

data class LineDetails(
  val id: Int,
  val code: String,
  val synoptic: String,
  val name: String,
  val information: String,
  val style: String,
  val routes: List<Route>,
  val incidences: List<Incidence>
)
