package org.galio.bussantiago.data.repository

import org.galio.bussantiago.core.model.BusStopFavorite
import org.galio.bussantiago.data.local.FavoriteDataSource

internal class BusStopFavoriteRepository(
  private val favoriteDataSource: FavoriteDataSource
) {

  fun getBusStopFavorites(): Result<List<BusStopFavorite>> =
    favoriteDataSource.getAll()

  fun remove(busStopFavorite: BusStopFavorite): Result<Unit> =
    favoriteDataSource.remove(busStopFavorite)

  fun add(busStopFavorite: BusStopFavorite): Result<Unit> =
    favoriteDataSource.save(busStopFavorite)
}
