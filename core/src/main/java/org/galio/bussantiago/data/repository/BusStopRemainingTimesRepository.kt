package org.galio.bussantiago.data.repository

import org.galio.bussantiago.core.Either
import org.galio.bussantiago.core.Either.Error
import org.galio.bussantiago.core.Either.Success
import org.galio.bussantiago.core.model.BusStopRemainingTimes
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.mapper.BusStopRemainingTimesMapper

internal class BusStopRemainingTimesRepository(
  private val apiClient: ApiClient,
  private val mapper: BusStopRemainingTimesMapper
) {

  fun getBusStopRemainingTimes(busStopCode: String): Either<Exception, BusStopRemainingTimes> {
    val response = apiClient.getBusStopRemainingTimes(busStopCode)
    return if (response.isSuccess) {
      Success(mapper.toDomain(response.successValue))
    } else {
      Error(response.errorValue)
    }
  }
}
