package org.galio.bussantiago.data

import org.galio.bussantiago.Either
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.cache.BusStopSearchCache
import org.galio.bussantiago.data.entity.BusStopRequest
import org.galio.bussantiago.data.mapper.BusStopSearchMapper
import org.galio.bussantiago.domain.model.BusStopSearch
import org.galio.bussantiago.domain.repository.SearchBusStopRepository

class SearchBusStopRepositoryImpl(
  private val apiClient: ApiClient,
  private val mapper: BusStopSearchMapper,
  private val cache: BusStopSearchCache
) : SearchBusStopRepository {

  override fun searchAllBusStops(): Either<Exception, List<BusStopSearch>> {
    val localData = cache.getAll()
    return if (localData.isNotEmpty()) {
      Either.Right(localData)
    } else {
      val serviceResult = apiClient.searchBusStop(BusStopRequest(""))
      if (serviceResult.isRight) {
        val busStops = serviceResult.rightValue.map { mapper.toDomain(it) }
        cache.save(busStops)
        Either.Right(busStops)
      } else {
        Either.Left(serviceResult.leftValue)
      }
    }
  }
}
