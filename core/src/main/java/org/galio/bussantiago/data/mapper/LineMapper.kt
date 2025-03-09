package org.galio.bussantiago.data.mapper

import org.galio.bussantiago.core.model.Line
import org.galio.bussantiago.data.entity.LineEntity

internal class LineMapper : Mapper<LineEntity, Line> {

  override fun toDomain(dataModel: LineEntity): Line {
    return Line(
      id = dataModel.id,
      code = dataModel.codigo,
      synoptic = dataModel.sinoptico,
      name = dataModel.nombre,
      company = dataModel.empresa,
      incidents = dataModel.incidencias,
      style = dataModel.estilo
    )
  }
}
