package org.galio.bussantiago.domain.interactor

import org.galio.bussantiago.Either
import org.galio.bussantiago.core.AddBusStopFavorite
import org.galio.bussantiago.data.repository.BusStopFavoriteRepository
import org.galio.bussantiago.domain.model.BusStopFavorite

internal class AddBusStopFavoriteImpl(
  private val busStopFavoriteRepository: BusStopFavoriteRepository
) : AddBusStopFavorite {

  override fun invoke(request: BusStopFavorite): Either<Exception, Unit> {
    return busStopFavoriteRepository.add(request)
  }
}
