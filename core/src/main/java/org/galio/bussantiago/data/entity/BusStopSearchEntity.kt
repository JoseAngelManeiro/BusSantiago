package org.galio.bussantiago.data.entity

data class BusStopSearchEntity(
  val id: Int,
  val codigo: String,
  val nombre: String,
  val zona: String?,
  val coordenadas: CoordinatesEntity,
  val lineas: List<LineSearchEntity>
)
