package org.galio.bussantiago.data.mapper

import org.galio.bussantiago.data.entity.BusStopEntity
import org.galio.bussantiago.domain.model.BusStop

class BusStopMapper(
  private val coordinatesMapper: CoordinatesMapper
) : Mapper<BusStopEntity, BusStop> {

  override fun toDomain(dataModel: BusStopEntity): BusStop {
    return BusStop(
      id = dataModel.id,
      code = dataModel.codigo,
      name = dataModel.nombre,
      zone = dataModel.zona,
      extraordinary = dataModel.extraordinaria,
      coordinates = coordinatesMapper.toDomain(dataModel.coordenadas)
    )
  }
}
