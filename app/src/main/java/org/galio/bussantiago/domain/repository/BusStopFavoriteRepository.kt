package org.galio.bussantiago.domain.repository

import org.galio.bussantiago.common.Either
import org.galio.bussantiago.domain.model.BusStopFavorite

interface BusStopFavoriteRepository {
  fun getBusStopFavorites(): Either<Exception, List<BusStopFavorite>>
  fun remove(busStopFavorite: BusStopFavorite): Either<Exception, Unit>
  fun add(busStopFavorite: BusStopFavorite): Either<Exception, Unit>
}
