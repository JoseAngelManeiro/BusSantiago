package org.galio.bussantiago.features.favorites

import org.galio.bussantiago.common.Either
import org.galio.bussantiago.domain.Interactor
import org.galio.bussantiago.domain.model.BusStopFavorite
import org.galio.bussantiago.domain.repository.BusStopFavoriteRepository

class GetBusStopFavorites(
  private val busStopFavoriteRepository: BusStopFavoriteRepository
) : Interactor<Unit, List<BusStopFavorite>> {

  override fun invoke(request: Unit): Either<Exception, List<BusStopFavorite>> {
    return busStopFavoriteRepository.getBusStopFavorites()
  }
}
