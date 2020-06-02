package org.galio.bussantiago.data

import org.galio.bussantiago.Either
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.entity.BusStopRequest
import org.galio.bussantiago.data.mapper.SearchBusStopMapper
import org.galio.bussantiago.domain.model.SearchBusStop
import org.galio.bussantiago.domain.repository.SearchBusStopRepository

class SearchBusStopRepositoryImpl(
  private val apiClient: ApiClient,
  private val mapper: SearchBusStopMapper
) : SearchBusStopRepository {

  override fun getSearchBusStops(
    code: String
  ): Either<Exception, List<SearchBusStop>> {
    return apiClient.searchBusStop(BusStopRequest(code)).fold(
      { exception -> Either.left(exception) },
      { list -> Either.right(list.map { mapper.toDomain(it) }) }
    )
  }
}
