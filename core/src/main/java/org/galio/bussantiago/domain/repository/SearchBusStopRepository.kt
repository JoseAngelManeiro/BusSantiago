package org.galio.bussantiago.domain.repository

import org.galio.bussantiago.Either
import org.galio.bussantiago.domain.model.BusStopSearch

interface SearchBusStopRepository {
  fun getSearchBusStops(code: String): Either<Exception, List<BusStopSearch>>
}
