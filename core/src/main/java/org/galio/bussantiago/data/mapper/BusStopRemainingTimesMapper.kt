package org.galio.bussantiago.data.mapper

import org.galio.bussantiago.data.entity.BusStopRemainingTimesEntity
import org.galio.bussantiago.domain.model.BusStopRemainingTimes

class BusStopRemainingTimesMapper(
  private val coordinatesMapper: CoordinatesMapper,
  private val lineRemainingTimeMapper: LineRemainingTimeMapper
) : Mapper<BusStopRemainingTimesEntity, BusStopRemainingTimes> {

  override fun toDomain(dataModel: BusStopRemainingTimesEntity): BusStopRemainingTimes {
    return BusStopRemainingTimes(
      id = dataModel.id,
      code = dataModel.codigo,
      name = dataModel.nombre,
      zone = dataModel.zona,
      coordinates = coordinatesMapper.toDomain(dataModel.coordenadas),
      lineRemainingTimes = dataModel.lineas.map { lineRemainingTimeMapper.toDomain(it) }
    )
  }
}
