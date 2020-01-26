package org.galio.bussantiago.data

import org.galio.bussantiago.Either
import org.galio.bussantiago.Either.Right
import org.galio.bussantiago.data.local.FavoriteDataSource
import org.galio.bussantiago.domain.model.BusStopFavorite
import org.galio.bussantiago.domain.repository.BusStopFavoriteRepository

class BusStopFavoriteRepositoryImpl(
  private val favoriteDataSource: FavoriteDataSource
) : BusStopFavoriteRepository {

  override fun getBusStopFavorites(): Either<Exception, List<BusStopFavorite>> {
    return Right(favoriteDataSource.getAll())
  }

  override fun remove(busStopFavorite: BusStopFavorite): Either<Exception, Unit> {
    return Right(favoriteDataSource.remove(busStopFavorite))
  }

  override fun add(busStopFavorite: BusStopFavorite): Either<Exception, Unit> {
    return Right(favoriteDataSource.save(busStopFavorite))
  }
}
