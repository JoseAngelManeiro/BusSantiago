package org.galio.bussantiago.core

import org.galio.bussantiago.core.model.BusStopFavorite

interface GetBusStopFavorites {
  operator fun invoke(): Result<List<BusStopFavorite>>
}
