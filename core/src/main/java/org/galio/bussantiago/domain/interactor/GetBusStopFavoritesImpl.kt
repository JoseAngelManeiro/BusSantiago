package org.galio.bussantiago.domain.interactor

import org.galio.bussantiago.Either
import org.galio.bussantiago.core.GetBusStopFavorites
import org.galio.bussantiago.data.repository.BusStopFavoriteRepository
import org.galio.bussantiago.domain.model.BusStopFavorite

internal class GetBusStopFavoritesImpl(
  private val busStopFavoriteRepository: BusStopFavoriteRepository
) : GetBusStopFavorites {

  override fun invoke(request: Unit): Either<Exception, List<BusStopFavorite>> {
    return busStopFavoriteRepository.getBusStopFavorites()
  }
}
