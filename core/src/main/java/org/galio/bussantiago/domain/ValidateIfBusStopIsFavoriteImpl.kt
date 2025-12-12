package org.galio.bussantiago.domain

import org.galio.bussantiago.core.ValidateIfBusStopIsFavorite
import org.galio.bussantiago.data.repository.BusStopFavoriteRepository

internal class ValidateIfBusStopIsFavoriteImpl(
  private val busStopFavoriteRepository: BusStopFavoriteRepository
) : ValidateIfBusStopIsFavorite {

  override fun invoke(busStopCode: String): Result<Boolean> {
    return busStopFavoriteRepository.getBusStopFavorites().map { busStopFavorites ->
      busStopFavorites.any { it.code == busStopCode }
    }
  }
}
