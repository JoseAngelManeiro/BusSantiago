package org.galio.bussantiago.data.mapper

import org.galio.bussantiago.data.entity.LineSearchEntity
import org.galio.bussantiago.domain.model.LineSearch

class LineSearchMapper : Mapper<LineSearchEntity, LineSearch> {

  override fun toDomain(dataModel: LineSearchEntity): LineSearch {
    return LineSearch(
      synoptic = dataModel.sinoptico,
      style = dataModel.estilo
    )
  }
}
