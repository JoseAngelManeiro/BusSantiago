package org.galio.bussantiago.domain.repository

import org.galio.bussantiago.common.Either
import org.galio.bussantiago.domain.model.Line

interface LineRepository {
  fun getLines(): Either<Exception, List<Line>>
}
