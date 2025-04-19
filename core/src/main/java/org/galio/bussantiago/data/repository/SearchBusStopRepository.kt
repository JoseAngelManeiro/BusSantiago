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
      Either.Success(localData)
    } else {
      val serviceResult = apiClient.searchBusStop(BusStopRequest(""))
      if (serviceResult.isSuccess) {
        val busStops = serviceResult.successValue.map { mapper.toDomain(it) }
        cache.save(busStops)
        Either.Success(busStops)
      } else {
        Either.Error(serviceResult.errorValue)
      }
    }
  }
}
