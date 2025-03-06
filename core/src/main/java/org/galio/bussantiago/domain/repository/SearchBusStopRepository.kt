package org.galio.bussantiago.domain.repository

import org.galio.bussantiago.Either
import org.galio.bussantiago.domain.model.BusStopSearch

internal interface SearchBusStopRepository {
  fun searchAllBusStops(): Either<Exception, List<BusStopSearch>>
}
