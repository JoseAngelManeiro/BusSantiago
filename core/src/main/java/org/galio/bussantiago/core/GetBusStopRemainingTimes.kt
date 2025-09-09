package org.galio.bussantiago.core

import org.galio.bussantiago.core.model.BusStopRemainingTimes

interface GetBusStopRemainingTimes {
  operator fun invoke(busStopCode: String): Result<BusStopRemainingTimes>
}
