package org.galio.bussantiago.domain

import org.galio.bussantiago.core.Either
import org.galio.bussantiago.core.ValidateIfBusStopIsFavorite
import org.galio.bussantiago.data.repository.BusStopFavoriteRepository

internal class ValidateIfBusStopIsFavoriteImpl(
  private val busStopFavoriteRepository: BusStopFavoriteRepository
) : ValidateIfBusStopIsFavorite {

  override fun invoke(request: String): Either<Exception, Boolean> {
    val response = busStopFavoriteRepository.getBusStopFavorites()
    return if (response.isSuccess) {
      val busStopFavorites = response.successValue
      if (busStopFavorites.isEmpty() ||
        busStopFavorites.find { it.code == request } == null
      ) {
        Either.Success(false)
      } else {
        Either.Success(true)
      }
    } else {
      Either.Error(response.errorValue)
    }
  }
}
