package org.galio.bussantiago.domain.interactor

import org.galio.bussantiago.Either
import org.galio.bussantiago.domain.model.BusStopFavorite
import org.galio.bussantiago.domain.repository.BusStopFavoriteRepository

class RemoveBusStopFavorite(
  private val busStopFavoriteRepository: BusStopFavoriteRepository
) : Interactor<BusStopFavorite, Unit> {

  override fun invoke(request: BusStopFavorite): Either<Exception, Unit> {
    return busStopFavoriteRepository.remove(request)
  }
}
