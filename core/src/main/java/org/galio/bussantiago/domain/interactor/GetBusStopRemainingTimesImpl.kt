package org.galio.bussantiago.domain.interactor

import org.galio.bussantiago.Either
import org.galio.bussantiago.core.GetBusStopRemainingTimes
import org.galio.bussantiago.domain.model.BusStopRemainingTimes
import org.galio.bussantiago.domain.repository.BusStopRemainingTimesRepository

internal class GetBusStopRemainingTimesImpl(
  private val busStopRemainingTimesRepository: BusStopRemainingTimesRepository
) : GetBusStopRemainingTimes {

  override fun invoke(request: String): Either<Exception, BusStopRemainingTimes> {
    return busStopRemainingTimesRepository.getBusStopRemainingTimes(request)
  }
}
