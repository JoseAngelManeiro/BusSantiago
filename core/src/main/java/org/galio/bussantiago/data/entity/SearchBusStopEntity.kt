package org.galio.bussantiago.data.entity

data class SearchBusStopEntity(
  val id: Int,
  val codigo: String,
  val nombre: String,
  val zona: String?,
  val coordenadas: CoordinatesEntity,
  val lineas: List<SearchLineEntity>
)
