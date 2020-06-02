package org.galio.bussantiago.domain.interactor

import org.galio.bussantiago.Either
import org.galio.bussantiago.domain.model.SearchBusStop
import org.galio.bussantiago.domain.repository.SearchBusStopRepository

class GetSearchBusStop(
  private val searchBusStopRepository: SearchBusStopRepository
) : Interactor<String, SearchBusStop?> {

  override fun invoke(request: String): Either<Exception, SearchBusStop?> {
    return searchBusStopRepository.getSearchBusStops(request).fold(
      { exception -> Either.Left(exception) },
      { searchBusStops -> Either.Right(searchBusStops.find { it.code == request }) }
    )
  }
}
