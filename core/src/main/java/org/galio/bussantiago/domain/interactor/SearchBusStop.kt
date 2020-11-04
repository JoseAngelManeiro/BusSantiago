package org.galio.bussantiago.domain.interactor

import org.galio.bussantiago.Either
import org.galio.bussantiago.domain.model.BusStopSearch
import org.galio.bussantiago.domain.model.NullBusStopSearch
import org.galio.bussantiago.domain.repository.SearchBusStopRepository

class SearchBusStop(
  private val searchBusStopRepository: SearchBusStopRepository
) : Interactor<String, BusStopSearch> {

  override fun invoke(request: String): Either<Exception, BusStopSearch> {
    return searchBusStopRepository.getSearchBusStops(request).fold(
      { exception -> Either.Left(exception) },
      { searchBusStops ->
        val index = searchBusStops.indexOfFirst { it.code == request }
        Either.Right(searchBusStops.getOrElse(index) { NullBusStopSearch() })
      }
    )
  }
}
