package org.galio.bussantiago.domain.interactor

import org.galio.bussantiago.Either
import org.galio.bussantiago.domain.repository.BusStopFavoriteRepository

class ValidateIfBusStopIsFavorite(
  private val busStopFavoriteRepository: BusStopFavoriteRepository
) : Interactor<String, Boolean> {

  override fun invoke(request: String): Either<Exception, Boolean> {
    val response = busStopFavoriteRepository.getBusStopFavorites()
    return if (response.isRight) {
      val busStopFavorites = response.rightValue
      if (busStopFavorites.isEmpty() ||
        busStopFavorites.find { it.code == request } == null) {
        Either.Right(false)
      } else {
        Either.Right(true)
      }
    } else {
      Either.Left(response.leftValue)
    }
  }
}
