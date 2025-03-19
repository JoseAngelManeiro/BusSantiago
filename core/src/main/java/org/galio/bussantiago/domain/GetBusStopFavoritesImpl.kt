package org.galio.bussantiago.domain

import org.galio.bussantiago.core.Either
import org.galio.bussantiago.core.GetBusStopFavorites
import org.galio.bussantiago.core.model.BusStopFavorite
import org.galio.bussantiago.data.repository.BusStopFavoriteRepository

internal class GetBusStopFavoritesImpl(
  private val busStopFavoriteRepository: BusStopFavoriteRepository
) : GetBusStopFavorites {

  override fun invoke(request: Unit): Either<Exception, List<BusStopFavorite>> {
    return busStopFavoriteRepository.getBusStopFavorites()
  }
}
