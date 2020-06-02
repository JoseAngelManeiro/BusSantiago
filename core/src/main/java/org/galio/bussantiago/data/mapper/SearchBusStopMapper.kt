package org.galio.bussantiago.data.mapper

import org.galio.bussantiago.data.entity.SearchBusStopEntity
import org.galio.bussantiago.domain.model.SearchBusStop

class SearchBusStopMapper(
  private val coordinatesMapper: CoordinatesMapper,
  private val searchLineMapper: SearchLineMapper
) : Mapper<SearchBusStopEntity, SearchBusStop> {

  override fun toDomain(dataModel: SearchBusStopEntity): SearchBusStop {
    return SearchBusStop(
      id = dataModel.id,
      code = dataModel.codigo,
      name = dataModel.nombre,
      zone = dataModel.zona,
      coordinates = coordinatesMapper.toDomain(dataModel.coordenadas),
      lines = dataModel.lineas.map { searchLineMapper.toDomain(it) }
    )
  }
}
