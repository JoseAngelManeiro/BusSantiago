package org.galio.bussantiago.data.repository

import org.galio.bussantiago.core.Either
import org.galio.bussantiago.core.model.BusStopSearch
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.cache.BusStopSearchCache
import org.galio.bussantiago.data.entity.BusStopRequest
import org.galio.bussantiago.data.mapper.BusStopSearchMapper

internal class SearchBusStopRepository(
  private val apiClient: ApiClient,
  private val mapper: BusStopSearchMapper,
  private val cache: BusStopSearchCache
) {

  fun searchAllBusStops(): Either<Exception, List<BusStopSearch>> {
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
