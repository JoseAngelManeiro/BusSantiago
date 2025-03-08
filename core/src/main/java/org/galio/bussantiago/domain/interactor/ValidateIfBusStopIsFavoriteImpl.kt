package org.galio.bussantiago.domain.interactor

import org.galio.bussantiago.Either
import org.galio.bussantiago.core.ValidateIfBusStopIsFavorite
import org.galio.bussantiago.data.repository.BusStopFavoriteRepository

internal class ValidateIfBusStopIsFavoriteImpl(
  private val busStopFavoriteRepository: BusStopFavoriteRepository
) : ValidateIfBusStopIsFavorite {

  override fun invoke(request: String): Either<Exception, Boolean> {
    val response = busStopFavoriteRepository.getBusStopFavorites()
    return if (response.isRight) {
      val busStopFavorites = response.rightValue
      if (busStopFavorites.isEmpty() ||
        busStopFavorites.find { it.code == request } == null
      ) {
        Either.Right(false)
      } else {
        Either.Right(true)
      }
    } else {
      Either.Left(response.leftValue)
    }
  }
}
