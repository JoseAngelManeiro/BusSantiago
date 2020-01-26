package org.galio.bussantiago.data.mapper

import org.galio.bussantiago.data.entity.RouteEntity
import org.galio.bussantiago.domain.model.Route

class RouteMapper(
  private val busStopMapper: BusStopMapper
) : Mapper<RouteEntity, Route> {

  override fun toDomain(dataModel: RouteEntity): Route {
    return Route(
      name = dataModel.nombre,
      direction = dataModel.sentido,
      busStops = dataModel.paradas.map { busStopMapper.toDomain(it) }
    )
  }
}
