package org.galio.bussantiago.data.mapper

import org.galio.bussantiago.data.entity.SearchLineEntity
import org.galio.bussantiago.domain.model.SearchLine

class SearchLineMapper : Mapper<SearchLineEntity, SearchLine> {

  override fun toDomain(dataModel: SearchLineEntity): SearchLine {
    return SearchLine(
      synoptic = dataModel.sinoptico,
      style = dataModel.estilo
    )
  }
}
