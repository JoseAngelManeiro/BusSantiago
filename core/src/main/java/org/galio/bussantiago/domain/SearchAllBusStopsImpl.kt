package org.galio.bussantiago.domain

import org.galio.bussantiago.core.Either
import org.galio.bussantiago.core.SearchAllBusStops
import org.galio.bussantiago.core.model.BusStopSearch
import org.galio.bussantiago.data.repository.SearchBusStopRepository

internal class SearchAllBusStopsImpl(
  private val searchBusStopRepository: SearchBusStopRepository
) : SearchAllBusStops {

  override fun invoke(request: Unit): Either<Exception, List<BusStopSearch>> {
    return searchBusStopRepository.searchAllBusStops()
  }
}
