package org.galio.bussantiago.domain

import org.galio.bussantiago.common.Either

interface LineRepository {
  fun getLines(): Either<Exception, List<String>>
}
