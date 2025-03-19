package org.galio.bussantiago.data.mapper

import org.galio.bussantiago.core.model.LineRemainingTime
import org.galio.bussantiago.data.entity.LineRemainingTimeEntity

internal class LineRemainingTimeMapper(
  private val dateMapper: DateMapper
) : Mapper<LineRemainingTimeEntity, LineRemainingTime> {

  override fun toDomain(dataModel: LineRemainingTimeEntity): LineRemainingTime {
    return LineRemainingTime(
      id = dataModel.id,
      synoptic = dataModel.sinoptico,
      name = dataModel.nombre,
      style = dataModel.estilo,
      nextArrival = dateMapper.getDate(dataModel.proximoPaso),
      minutesUntilNextArrival = dataModel.minutosProximoPaso
    )
  }
}
