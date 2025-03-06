package org.galio.bussantiago.domain.repository

import org.galio.bussantiago.Either
import org.galio.bussantiago.domain.model.Line

internal interface LineRepository {
  fun getLines(): Either<Exception, List<Line>>
}
