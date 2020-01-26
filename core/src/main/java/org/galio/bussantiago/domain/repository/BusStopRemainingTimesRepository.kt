package org.galio.bussantiago.domain.repository

import org.galio.bussantiago.Either
import org.galio.bussantiago.domain.model.BusStopRemainingTimes

interface BusStopRemainingTimesRepository {
  fun getBusStopRemainingTimes(busStopCode: String): Either<Exception, BusStopRemainingTimes>
}
