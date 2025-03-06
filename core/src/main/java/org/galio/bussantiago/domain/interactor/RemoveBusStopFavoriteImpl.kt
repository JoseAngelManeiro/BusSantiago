package org.galio.bussantiago.domain.interactor

import org.galio.bussantiago.Either
import org.galio.bussantiago.core.RemoveBusStopFavorite
import org.galio.bussantiago.domain.model.BusStopFavorite
import org.galio.bussantiago.domain.repository.BusStopFavoriteRepository

internal class RemoveBusStopFavoriteImpl(
  private val busStopFavoriteRepository: BusStopFavoriteRepository
) : RemoveBusStopFavorite {

  override fun invoke(request: BusStopFavorite): Either<Exception, Unit> {
    return busStopFavoriteRepository.remove(request)
  }
}
