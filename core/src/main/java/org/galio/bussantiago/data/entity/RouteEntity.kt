package org.galio.bussantiago.data.entity

internal data class RouteEntity(
  val nombre: String,
  val sentido: String,
  val paradas: List<BusStopEntity>
)
