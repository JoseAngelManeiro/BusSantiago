package org.galio.bussantiago.domain.interactor

import org.galio.bussantiago.Either
import org.galio.bussantiago.domain.model.BusStopSearch
import org.galio.bussantiago.domain.repository.SearchBusStopRepository

class SearchAllBusStops(
  private val searchBusStopRepository: SearchBusStopRepository
) : Interactor<Unit, List<BusStopSearch>> {

  override fun invoke(request: Unit): Either<Exception, List<BusStopSearch>> {
    return searchBusStopRepository.searchAllBusStops()
  }
}
