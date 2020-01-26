package org.galio.bussantiago.domain.interactor

import org.galio.bussantiago.Either
import org.galio.bussantiago.domain.model.BusStopFavorite
import org.galio.bussantiago.domain.repository.BusStopFavoriteRepository

class GetBusStopFavorites(
  private val busStopFavoriteRepository: BusStopFavoriteRepository
) :
  Interactor<Unit, List<BusStopFavorite>> {

  override fun invoke(request: Unit): Either<Exception, List<BusStopFavorite>> {
    return busStopFavoriteRepository.getBusStopFavorites()
  }
}
