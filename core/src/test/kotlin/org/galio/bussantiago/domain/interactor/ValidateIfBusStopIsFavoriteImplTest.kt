package org.galio.bussantiago.domain.interactor

import org.galio.bussantiago.Either
import org.galio.bussantiago.domain.model.BusStopFavorite
import org.galio.bussantiago.domain.repository.BusStopFavoriteRepository
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class ValidateIfBusStopIsFavoriteImplTest {

  private val busStopFavoriteRepository = Mockito.mock(BusStopFavoriteRepository::class.java)
  private val validateIfBusStopIsFavorite =
    ValidateIfBusStopIsFavoriteImpl(busStopFavoriteRepository)

  @Test
  fun `returns false if the received list is empty`() {
    val busStopFavoritesStub = emptyList<BusStopFavorite>()
    `when`(busStopFavoriteRepository.getBusStopFavorites())
      .thenReturn(Either.Right(busStopFavoritesStub))

    val result = validateIfBusStopIsFavorite("1234")

    assertEquals(false, result.rightValue)
  }

  @Test
  fun `returns false if the received list does not have the bus stop`() {
    val busStopFavoritesStub = listOf(
      BusStopFavorite("111", "Any"),
      BusStopFavorite("222", "Any")
    )
    `when`(busStopFavoriteRepository.getBusStopFavorites())
      .thenReturn(Either.Right(busStopFavoritesStub))

    val result = validateIfBusStopIsFavorite("1234")

    assertEquals(false, result.rightValue)
  }

  @Test
  fun `returns true if the received list has the bus stop`() {
    val busStopFavoritesStub = listOf(
      BusStopFavorite("111", "Any"),
      BusStopFavorite("222", "Any")
    )
    `when`(busStopFavoriteRepository.getBusStopFavorites())
      .thenReturn(Either.Right(busStopFavoritesStub))

    val result = validateIfBusStopIsFavorite("111")

    assertEquals(true, result.rightValue)
  }

  @Test
  fun `if the repository fails, returns the exception received`() {
    val exceptionStub = Exception()
    `when`(busStopFavoriteRepository.getBusStopFavorites())
      .thenReturn(Either.Left(exceptionStub))

    val result = validateIfBusStopIsFavorite("1234")

    assertEquals(exceptionStub, result.leftValue)
  }
}
