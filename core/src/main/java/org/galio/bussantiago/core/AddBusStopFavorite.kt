package org.galio.bussantiago.core

import org.galio.bussantiago.core.model.BusStopFavorite

interface AddBusStopFavorite {
  operator fun invoke(busStopFavorite: BusStopFavorite): Result<Unit>
}
