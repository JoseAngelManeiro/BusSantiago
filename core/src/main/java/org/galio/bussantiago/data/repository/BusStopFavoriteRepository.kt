package org.galio.bussantiago.data.repository

import org.galio.bussantiago.core.Either
import org.galio.bussantiago.core.model.BusStopFavorite
import org.galio.bussantiago.data.local.FavoriteDataSource

internal class BusStopFavoriteRepository(
  private val favoriteDataSource: FavoriteDataSource
) {

  fun getBusStopFavorites(): Either<Exception, List<BusStopFavorite>> =
    favoriteDataSource.getAll()

  fun remove(busStopFavorite: BusStopFavorite): Either<Exception, Unit> =
    favoriteDataSource.remove(busStopFavorite)

  fun add(busStopFavorite: BusStopFavorite): Either<Exception, Unit> =
    favoriteDataSource.save(busStopFavorite)
}
