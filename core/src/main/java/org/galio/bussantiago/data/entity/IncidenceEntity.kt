package org.galio.bussantiago.data.entity

internal data class IncidenceEntity(
  val id: Int,
  val titulo: String,
  val descripcion: String,
  val inicio: String,
  val fin: String?
)
