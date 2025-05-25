package org.galio.bussantiago.data.repository

import org.galio.bussantiago.core.model.BusStopRemainingTimes
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.mapper.BusStopRemainingTimesMapper

internal class BusStopRemainingTimesRepository(
  private val apiClient: ApiClient,
  private val mapper: BusStopRemainingTimesMapper
) {

  fun getBusStopRemainingTimes(busStopCode: String): Result<BusStopRemainingTimes> {
    return apiClient.getBusStopRemainingTimes(busStopCode).map { busStopRemainingTimesEntity ->
      mapper.toDomain(busStopRemainingTimesEntity)
    }
  }
}
