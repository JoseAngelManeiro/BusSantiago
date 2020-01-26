package org.galio.bussantiago.domain.repository

import org.galio.bussantiago.Either
import org.galio.bussantiago.domain.model.LineDetails

interface LineDetailsRepository {
  fun getLineDetails(id: Int): Either<Exception, LineDetails>
}
