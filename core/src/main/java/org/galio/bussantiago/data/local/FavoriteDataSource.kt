package org.galio.bussantiago.data.local

import org.galio.bussantiago.core.model.BusStopFavorite

internal interface FavoriteDataSource {

  fun getAll(): Result<List<BusStopFavorite>>

  fun remove(busStopFavorite: BusStopFavorite): Result<Unit>

  fun save(busStopFavorite: BusStopFavorite): Result<Unit>
}
