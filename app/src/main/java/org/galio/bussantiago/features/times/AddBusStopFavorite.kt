package org.galio.bussantiago.features.times

import org.galio.bussantiago.common.Either
import org.galio.bussantiago.domain.Interactor
import org.galio.bussantiago.domain.model.BusStopFavorite
import org.galio.bussantiago.domain.repository.BusStopFavoriteRepository

class AddBusStopFavorite(
  private val busStopFavoriteRepository: BusStopFavoriteRepository
) : Interactor<BusStopFavorite, Unit> {

  override fun invoke(request: BusStopFavorite): Either<Exception, Unit> {
    return busStopFavoriteRepository.add(request)
  }
}
