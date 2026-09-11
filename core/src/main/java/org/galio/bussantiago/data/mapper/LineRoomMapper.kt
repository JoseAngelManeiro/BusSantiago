package org.galio.bussantiago.data.mapper

import org.galio.bussantiago.core.model.Line
import org.galio.bussantiago.data.local.room.entity.LineEntity

internal class LineRoomMapper : Mapper<LineEntity, Line> {

  override fun toDomain(dataModel: LineEntity): Line {
    return Line(
      id = dataModel.id,
      code = dataModel.code,
      synoptic = dataModel.synoptic,
      name = dataModel.name,
      company = dataModel.company,
      incidents = dataModel.incidents,
      style = dataModel.style
    )
  }

  fun toEntity(domainModel: Line): LineEntity {
    return LineEntity(
      id = domainModel.id,
      code = domainModel.code,
      synoptic = domainModel.synoptic,
      name = domainModel.name,
      company = domainModel.company,
      incidents = domainModel.incidents,
      style = domainModel.style
    )
  }
}
