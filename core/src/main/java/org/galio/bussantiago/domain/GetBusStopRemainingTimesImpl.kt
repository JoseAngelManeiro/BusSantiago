package org.galio.bussantiago.domain

import org.galio.bussantiago.core.Either
import org.galio.bussantiago.core.GetBusStopRemainingTimes
import org.galio.bussantiago.core.model.BusStopRemainingTimes
import org.galio.bussantiago.data.repository.BusStopRemainingTimesRepository

internal class GetBusStopRemainingTimesImpl(
  private val busStopRemainingTimesRepository: BusStopRemainingTimesRepository
) : GetBusStopRemainingTimes {

  override fun invoke(request: String): Either<Exception, BusStopRemainingTimes> {
    return busStopRemainingTimesRepository.getBusStopRemainingTimes(request)
  }
}
