package org.galio.bussantiago.data.repository

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

  fun searchAllBusStops(): Result<List<BusStopSearch>> {
    val cachedResult = cache.getAll()
    return if (cachedResult.isSuccess) {
      cachedResult
    } else {
      apiClient.searchBusStop(BusStopRequest("")).map { entities ->
        entities.map { busStopSearchEntity ->
          mapper.toDomain(busStopSearchEntity)
        }.also {
          cache.save(it)
        }
      }
    }
  }
}
