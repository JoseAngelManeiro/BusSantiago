package org.galio.bussantiago.core

interface ValidateIfBusStopIsFavorite {
  operator fun invoke(busStopCode: String): Result<Boolean>
}
