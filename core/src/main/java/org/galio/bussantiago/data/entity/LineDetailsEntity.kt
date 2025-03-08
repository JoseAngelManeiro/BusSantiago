package org.galio.bussantiago.data.entity

internal data class LineDetailsEntity(
  val id: Int,
  val codigo: String,
  val sinoptico: String,
  val nombre: String,
  val informacion: String,
  val estilo: String,
  val trayectos: List<RouteEntity>,
  val incidencias: List<IncidenceEntity>
)
