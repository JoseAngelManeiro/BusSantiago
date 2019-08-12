package org.galio.bussantiago.features.times

import org.galio.bussantiago.common.Either
import org.galio.bussantiago.domain.Interactor
import org.galio.bussantiago.domain.model.BusStopRemainingTimes
import org.galio.bussantiago.domain.repository.BusStopRemainingTimesRepository

class GetBusStopRemainingTimes(
  private val busStopRemainingTimesRepository: BusStopRemainingTimesRepository
) : Interactor<String, BusStopRemainingTimes> {

  override fun invoke(request: String): Either<Exception, BusStopRemainingTimes> {
    return busStopRemainingTimesRepository.getBusStopRemainingTimes(request)
  }
}
