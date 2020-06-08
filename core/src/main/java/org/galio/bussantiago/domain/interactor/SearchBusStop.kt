package org.galio.bussantiago.domain.interactor

import org.galio.bussantiago.Either
import org.galio.bussantiago.domain.model.BusStopSearch
import org.galio.bussantiago.domain.repository.SearchBusStopRepository

class SearchBusStop(
  private val searchBusStopRepository: SearchBusStopRepository
) : Interactor<String, BusStopSearch?> {

  override fun invoke(request: String): Either<Exception, BusStopSearch?> {
    return searchBusStopRepository.getSearchBusStops(request).fold(
      { exception -> Either.Left(exception) },
      { searchBusStops -> Either.Right(searchBusStops.find { it.code == request }) }
    )
  }
}
