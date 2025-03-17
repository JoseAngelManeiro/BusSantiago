package org.galio.bussantiago.data.mapper

import org.galio.bussantiago.core.model.BusStopSearch
import org.galio.bussantiago.data.entity.BusStopSearchEntity

internal class BusStopSearchMapper(
  private val coordinatesMapper: CoordinatesMapper,
  private val lineSearchMapper: LineSearchMapper
) : Mapper<BusStopSearchEntity, BusStopSearch> {

  override fun toDomain(dataModel: BusStopSearchEntity): BusStopSearch {
    return BusStopSearch(
      id = dataModel.id,
      code = dataModel.codigo.removeLeadingZeros(),
      name = dataModel.nombre,
      zone = dataModel.zona,
      coordinates = coordinatesMapper.toDomain(dataModel.coordenadas),
      lines = dataModel.lineas.map { lineSearchMapper.toDomain(it) }
    )
  }

  // Removes all leading '0' characters from the String
  // If the String consists only of zeros, return "0" to avoid ending up with an empty String
  private fun String.removeLeadingZeros() = trimStart('0').ifEmpty { "0" }
}
