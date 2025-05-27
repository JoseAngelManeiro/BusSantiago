package org.galio.bussantiago.domain

import org.galio.bussantiago.core.RemoveBusStopFavorite
import org.galio.bussantiago.core.model.BusStopFavorite
import org.galio.bussantiago.data.repository.BusStopFavoriteRepository

internal class RemoveBusStopFavoriteImpl(
  private val busStopFavoriteRepository: BusStopFavoriteRepository
) : RemoveBusStopFavorite {

  override fun invoke(request: BusStopFavorite): Result<Unit> {
    return busStopFavoriteRepository.remove(request)
  }
}
