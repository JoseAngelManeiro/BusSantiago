package org.galio.bussantiago.data.entity

internal data class LineEntity(
  val id: Int,
  val codigo: String,
  val sinoptico: String,
  val nombre: String,
  val empresa: String,
  val incidencias: Int,
  val estilo: String
)
