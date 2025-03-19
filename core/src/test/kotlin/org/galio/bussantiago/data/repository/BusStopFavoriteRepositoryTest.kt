package org.galio.bussantiago.data.repository

import org.galio.bussantiago.core.Either
import org.galio.bussantiago.core.model.BusStopFavorite
import org.galio.bussantiago.data.exception.DatabaseException
import org.galio.bussantiago.data.local.FavoriteDataSource
import org.galio.bussantiago.util.mock
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.whenever

class BusStopFavoriteRepositoryTest {

  private val favoriteDataSource = mock<FavoriteDataSource>()
  private val busStopFavoriteRepository = BusStopFavoriteRepository(favoriteDataSource)

  @Test
  fun `when getBusStopFavorites should return the data source result`() {
    val favorites = listOf(mock<BusStopFavorite>())
    whenever(favoriteDataSource.getAll()).thenReturn(Either.Right(favorites))

    val result = busStopFavoriteRepository.getBusStopFavorites()

    assertEquals(favorites, result.rightValue)
  }

  @Test
  fun `when getBusStopFavorites should return the data source exception`() {
    val exception = DatabaseException()
    whenever(favoriteDataSource.getAll()).thenReturn(Either.Left(exception))

    val result = busStopFavoriteRepository.getBusStopFavorites()

    assertEquals(exception, result.leftValue)
  }

  @Test
  fun `when remove a favorite successfully should return Unit`() {
    val busStopFavorite = mock<BusStopFavorite>()
    whenever(favoriteDataSource.remove(busStopFavorite)).thenReturn(Either.Right(Unit))

    val result = busStopFavoriteRepository.remove(busStopFavorite)

    assertEquals(Unit, result.rightValue)
  }

  @Test
  fun `when remove a favorite fails should return data source exception`() {
    val exception = DatabaseException()
    val busStopFavorite = mock<BusStopFavorite>()
    whenever(favoriteDataSource.remove(busStopFavorite)).thenReturn(Either.Left(exception))

    val result = busStopFavoriteRepository.remove(busStopFavorite)

    assertEquals(exception, result.leftValue)
  }

  @Test
  fun `when add a favorite successfully should return Unit`() {
    val busStopFavorite = mock<BusStopFavorite>()
    whenever(favoriteDataSource.save(busStopFavorite)).thenReturn(Either.Right(Unit))

    val result = busStopFavoriteRepository.add(busStopFavorite)

    assertEquals(Unit, result.rightValue)
  }

  @Test
  fun `when add a favorite fails should return data source exception`() {
    val exception = DatabaseException()
    val busStopFavorite = mock<BusStopFavorite>()
    whenever(favoriteDataSource.save(busStopFavorite)).thenReturn(Either.Left(exception))

    val result = busStopFavoriteRepository.add(busStopFavorite)

    assertEquals(exception, result.leftValue)
  }
}
