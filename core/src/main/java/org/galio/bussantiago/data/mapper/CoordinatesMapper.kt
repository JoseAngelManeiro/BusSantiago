package org.galio.bussantiago.data.mapper

import org.galio.bussantiago.data.entity.CoordinatesEntity
import org.galio.bussantiago.domain.model.Coordinates

internal class CoordinatesMapper : Mapper<CoordinatesEntity, Coordinates> {

  override fun toDomain(dataModel: CoordinatesEntity): Coordinates {
    return Coordinates(
      latitude = dataModel.latitud,
      longitude = dataModel.longitud
    )
  }
}
