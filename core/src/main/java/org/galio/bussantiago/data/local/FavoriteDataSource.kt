package org.galio.bussantiago.data.local

import org.galio.bussantiago.core.model.BusStopFavorite

internal interface FavoriteDataSource {

  fun getAll(): List<BusStopFavorite>

  fun remove(busStopFavorite: BusStopFavorite)

  fun save(busStopFavorite: BusStopFavorite)
}
