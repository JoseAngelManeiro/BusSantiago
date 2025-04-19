package org.galio.bussantiago.domain

import org.galio.bussantiago.core.AddBusStopFavorite
import org.galio.bussantiago.core.Either
import org.galio.bussantiago.core.model.BusStopFavorite
import org.galio.bussantiago.data.repository.BusStopFavoriteRepository

internal class AddBusStopFavoriteImpl(
  private val busStopFavoriteRepository: BusStopFavoriteRepository
) : AddBusStopFavorite {

  override fun invoke(request: BusStopFavorite): Either<Exception, Unit> {
    return busStopFavoriteRepository.add(request)
  }
}
