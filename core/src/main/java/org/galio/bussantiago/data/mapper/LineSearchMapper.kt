package org.galio.bussantiago.data.mapper

import org.galio.bussantiago.core.model.LineSearch
import org.galio.bussantiago.data.entity.LineSearchEntity

internal class LineSearchMapper : Mapper<LineSearchEntity, LineSearch> {

  override fun toDomain(dataModel: LineSearchEntity): LineSearch {
    return LineSearch(
      synoptic = dataModel.sinoptico,
      style = dataModel.estilo
    )
  }
}
