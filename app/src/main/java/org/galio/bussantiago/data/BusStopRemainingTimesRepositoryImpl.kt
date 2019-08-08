package org.galio.bussantiago.data

import org.galio.bussantiago.common.Either
import org.galio.bussantiago.common.Either.Right
import org.galio.bussantiago.common.Either.Left
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.mapper.BusStopRemainingTimesMapper
import org.galio.bussantiago.domain.model.BusStopRemainingTimes
import org.galio.bussantiago.domain.repository.BusStopRemainingTimesRepository

internal class BusStopRemainingTimesRepositoryImpl(
  private val apiClient: ApiClient,
  private val mapper: BusStopRemainingTimesMapper
) : BusStopRemainingTimesRepository {

  override fun getBusStopRemainingTimes(
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
