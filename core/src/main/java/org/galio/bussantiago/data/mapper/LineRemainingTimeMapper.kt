package org.galio.bussantiago.data.mapper

import org.galio.bussantiago.data.entity.LineRemainingTimeEntity
import org.galio.bussantiago.domain.model.LineRemainingTime
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal class LineRemainingTimeMapper : Mapper<LineRemainingTimeEntity, LineRemainingTime> {

  override fun toDomain(dataModel: LineRemainingTimeEntity): LineRemainingTime {
    return LineRemainingTime(
      id = dataModel.id,
      synoptic = dataModel.sinoptico,
      name = dataModel.nombre,
      style = dataModel.estilo,
      nextArrival = getDate(dataModel.proximoPaso),
      minutesUntilNextArrival = dataModel.minutosProximoPaso
    )
  }

  private fun getDate(stringDate: String): Date? {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
    return formatter.parse(stringDate)
  }
}
