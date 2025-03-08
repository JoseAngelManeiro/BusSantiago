package org.galio.bussantiago.domain.interactor

import org.galio.bussantiago.Either
import org.galio.bussantiago.core.RemoveBusStopFavorite
import org.galio.bussantiago.data.repository.BusStopFavoriteRepository
import org.galio.bussantiago.domain.model.BusStopFavorite

internal class RemoveBusStopFavoriteImpl(
  private val busStopFavoriteRepository: BusStopFavoriteRepository
) : RemoveBusStopFavorite {

  override fun invoke(request: BusStopFavorite): Either<Exception, Unit> {
    return busStopFavoriteRepository.remove(request)
  }
}
