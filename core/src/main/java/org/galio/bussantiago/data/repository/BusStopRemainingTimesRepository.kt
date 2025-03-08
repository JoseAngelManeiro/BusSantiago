package org.galio.bussantiago.data.repository

import org.galio.bussantiago.Either
import org.galio.bussantiago.Either.Left
import org.galio.bussantiago.Either.Right
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.mapper.BusStopRemainingTimesMapper
import org.galio.bussantiago.domain.model.BusStopRemainingTimes

internal class BusStopRemainingTimesRepository(
  private val apiClient: ApiClient,
  private val mapper: BusStopRemainingTimesMapper
) {

  fun getBusStopRemainingTimes(
    busStopCode: String
  ): Either<Exception, BusStopRemainingTimes> {
    val response = apiClient.getBusStopRemainingTimes(busStopCode)
    return if (response.isRight) {
      Right(mapper.toDomain(response.rightValue))
    } else {
      Left(response.leftValue)
    }
  }
}
