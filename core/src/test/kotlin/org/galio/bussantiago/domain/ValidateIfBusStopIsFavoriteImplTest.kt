package org.galio.bussantiago.domain

import org.galio.bussantiago.core.model.BusStopFavorite
import org.galio.bussantiago.data.repository.BusStopFavoriteRepository
import org.galio.bussantiago.util.mock
import org.galio.bussantiago.util.thenFailure
import org.galio.bussantiago.util.thenSuccess
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.whenever

class ValidateIfBusStopIsFavoriteImplTest {

  private val busStopFavoriteRepository = mock<BusStopFavoriteRepository>()
  private val validateIfBusStopIsFavorite =
    ValidateIfBusStopIsFavoriteImpl(busStopFavoriteRepository)

  @Test
  fun `returns false if the received list is empty`() {
    val busStopFavoritesStub = emptyList<BusStopFavorite>()
    whenever(busStopFavoriteRepository.getBusStopFavorites()).thenSuccess(busStopFavoritesStub)

    val result = validateIfBusStopIsFavorite("1234")

    assertEquals(false, result.getOrNull())
  }

  @Test
  fun `returns false if the received list does not have the bus stop`() {
    val busStopFavoritesStub = listOf(
      BusStopFavorite("111", "Any"),
      BusStopFavorite("222", "Any")
    )
    whenever(busStopFavoriteRepository.getBusStopFavorites()).thenSuccess(busStopFavoritesStub)

    val result = validateIfBusStopIsFavorite("1234")

    assertEquals(false, result.getOrNull())
  }

  @Test
  fun `returns true if the received list has the bus stop`() {
    val busStopFavoritesStub = listOf(
      BusStopFavorite("111", "Any"),
      BusStopFavorite("222", "Any")
    )
    whenever(busStopFavoriteRepository.getBusStopFavorites()).thenSuccess(busStopFavoritesStub)

    val result = validateIfBusStopIsFavorite("111")

    assertEquals(true, result.getOrNull())
  }

  @Test
  fun `if the repository fails, returns the exception received`() {
    val exceptionStub = Exception()
    whenever(busStopFavoriteRepository.getBusStopFavorites()).thenFailure(exceptionStub)

    val result = validateIfBusStopIsFavorite("1234")

    assertEquals(exceptionStub, result.exceptionOrNull())
  }
}
