package org.galio.bussantiago.data.mapper

import org.galio.bussantiago.data.entity.IncidenceEntity
import org.galio.bussantiago.domain.model.Incidence
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal class IncidenceMapper : Mapper<IncidenceEntity, Incidence> {

  override fun toDomain(dataModel: IncidenceEntity): Incidence {
    return Incidence(
      id = dataModel.id,
      title = dataModel.titulo,
      description = dataModel.descripcion,
      startDate = getDate(dataModel.inicio),
      endDate = dataModel.fin?.let { getDate(it) }
    )
  }

  private fun getDate(stringDate: String): Date {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
    return formatter.parse(stringDate)
  }
}
