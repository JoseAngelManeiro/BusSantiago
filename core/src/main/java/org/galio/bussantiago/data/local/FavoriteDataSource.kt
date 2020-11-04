package org.galio.bussantiago.data.local

import org.galio.bussantiago.domain.model.BusStopFavorite

interface FavoriteDataSource {

  fun getAll(): List<BusStopFavorite>

  fun remove(busStopFavorite: BusStopFavorite)

  fun save(busStopFavorite: BusStopFavorite)
}
