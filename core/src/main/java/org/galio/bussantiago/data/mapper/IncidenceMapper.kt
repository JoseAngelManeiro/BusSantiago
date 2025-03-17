package org.galio.bussantiago.data.mapper

import org.galio.bussantiago.core.model.Incidence
import org.galio.bussantiago.data.entity.IncidenceEntity

internal class IncidenceMapper(
  private val dateMapper: DateMapper
) : Mapper<IncidenceEntity, Incidence> {

  override fun toDomain(dataModel: IncidenceEntity): Incidence {
    return Incidence(
      id = dataModel.id,
      title = dataModel.titulo,
      description = dataModel.descripcion,
      startDate = dateMapper.getDate(dataModel.inicio),
      endDate = dataModel.fin?.let { dateMapper.getDate(it) }
    )
  }
}
