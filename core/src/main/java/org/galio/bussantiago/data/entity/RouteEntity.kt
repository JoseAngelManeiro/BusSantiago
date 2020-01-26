package org.galio.bussantiago.data.entity

data class RouteEntity(
  val nombre: String,
  val sentido: String,
  val paradas: List<BusStopEntity>
)
