package org.galio.bussantiago.domain.interactor

import org.galio.bussantiago.Either
import org.galio.bussantiago.core.SearchAllBusStops
import org.galio.bussantiago.domain.model.BusStopSearch
import org.galio.bussantiago.domain.repository.SearchBusStopRepository

internal class SearchAllBusStopsImpl(
  private val searchBusStopRepository: SearchBusStopRepository
) : SearchAllBusStops {

  override fun invoke(request: Unit): Either<Exception, List<BusStopSearch>> {
    return searchBusStopRepository.searchAllBusStops()
  }
}
