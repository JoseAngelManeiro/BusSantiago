package org.galio.bussantiago.data.local

import org.galio.bussantiago.core.Either
import org.galio.bussantiago.core.model.BusStopFavorite

internal interface FavoriteDataSource {

  fun getAll(): Either<Exception, List<BusStopFavorite>>

  fun remove(busStopFavorite: BusStopFavorite): Either<Exception, Unit>

  fun save(busStopFavorite: BusStopFavorite): Either<Exception, Unit>
}
