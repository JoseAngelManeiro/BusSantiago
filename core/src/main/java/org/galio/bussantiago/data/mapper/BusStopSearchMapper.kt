package org.galio.bussantiago.data.mapper

import org.galio.bussantiago.data.entity.BusStopSearchEntity
import org.galio.bussantiago.domain.model.BusStopSearch

class BusStopSearchMapper(
  private val coordinatesMapper: CoordinatesMapper,
  private val lineSearchMapper: LineSearchMapper
) : Mapper<BusStopSearchEntity, BusStopSearch> {

  override fun toDomain(dataModel: BusStopSearchEntity): BusStopSearch {
    return BusStopSearch(
      id = dataModel.id,
      code = dataModel.codigo.toInt().toString(),
      name = dataModel.nombre,
      zone = dataModel.zona,
      coordinates = coordinatesMapper.toDomain(dataModel.coordenadas),
      lines = dataModel.lineas.map { lineSearchMapper.toDomain(it) }
    )
  }
}
