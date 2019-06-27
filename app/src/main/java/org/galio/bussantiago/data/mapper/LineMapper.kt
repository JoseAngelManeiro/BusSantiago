package org.galio.bussantiago.data.mapper

import org.galio.bussantiago.data.entity.LineEntity
import org.galio.bussantiago.domain.Line

class LineMapper : Mapper<LineEntity, Line> {

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
