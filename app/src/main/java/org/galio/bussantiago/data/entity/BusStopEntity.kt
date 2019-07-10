package org.galio.bussantiago.data.entity

data class BusStopEntity(
  val id: Int,
  val codigo: String,
  val nombre: String,
  val zona: String,
  val extraordinaria: Boolean,
  val coordenadas: CoordinatesEntity
)
