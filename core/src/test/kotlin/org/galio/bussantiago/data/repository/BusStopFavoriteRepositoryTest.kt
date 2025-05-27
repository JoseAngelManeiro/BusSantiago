package org.galio.bussantiago.data.repository

import org.galio.bussantiago.core.model.BusStopFavorite
import org.galio.bussantiago.data.exception.DatabaseException
import org.galio.bussantiago.data.local.FavoriteDataSource
import org.galio.bussantiago.util.mock
import org.galio.bussantiago.util.thenFailure
import org.galio.bussantiago.util.thenSuccess
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.whenever

class BusStopFavoriteRepositoryTest {

  private val favoriteDataSource = mock<FavoriteDataSource>()
  private val busStopFavoriteRepository = BusStopFavoriteRepository(favoriteDataSource)

  @Test
  fun `when getBusStopFavorites should return the data source result`() {
    val favorites = listOf(mock<BusStopFavorite>())
    whenever(favoriteDataSource.getAll()).thenSuccess(favorites)

    val result = busStopFavoriteRepository.getBusStopFavorites()

    assertEquals(favorites, result.getOrNull())
  }

  @Test
  fun `when getBusStopFavorites should return the data source exception`() {
    val exception = DatabaseException()
    whenever(favoriteDataSource.getAll()).thenFailure(exception)

    val result = busStopFavoriteRepository.getBusStopFavorites()

    assertEquals(exception, result.exceptionOrNull())
  }

  @Test
  fun `when remove a favorite successfully should return Unit`() {
    val busStopFavorite = mock<BusStopFavorite>()
    whenever(favoriteDataSource.remove(busStopFavorite)).thenSuccess(Unit)

    val result = busStopFavoriteRepository.remove(busStopFavorite)

    assertEquals(Unit, result.getOrNull())
  }

  @Test
  fun `when remove a favorite fails should return data source exception`() {
    val exception = DatabaseException()
    val busStopFavorite = mock<BusStopFavorite>()
    whenever(favoriteDataSource.remove(busStopFavorite)).thenFailure(exception)

    val result = busStopFavoriteRepository.remove(busStopFavorite)

    assertEquals(exception, result.exceptionOrNull())
  }

  @Test
  fun `when add a favorite successfully should return Unit`() {
    val busStopFavorite = mock<BusStopFavorite>()
    whenever(favoriteDataSource.save(busStopFavorite)).thenSuccess(Unit)

    val result = busStopFavoriteRepository.add(busStopFavorite)

    assertEquals(Unit, result.getOrNull())
  }

  @Test
  fun `when add a favorite fails should return data source exception`() {
    val exception = DatabaseException()
    val busStopFavorite = mock<BusStopFavorite>()
    whenever(favoriteDataSource.save(busStopFavorite)).thenFailure(exception)

    val result = busStopFavoriteRepository.add(busStopFavorite)

    assertEquals(exception, result.exceptionOrNull())
  }
}
