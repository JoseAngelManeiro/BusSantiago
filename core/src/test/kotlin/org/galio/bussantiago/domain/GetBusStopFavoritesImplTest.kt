package org.galio.bussantiago.domain

import org.galio.bussantiago.data.repository.BusStopFavoriteRepository
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify

class GetBusStopFavoritesImplTest {

  private val busStopFavoriteRepository = Mockito.mock(BusStopFavoriteRepository::class.java)
  private val getBusStopFavorites = GetBusStopFavoritesImpl(busStopFavoriteRepository)

  @Test
  fun `invokes the repository`() {
    getBusStopFavorites(Unit)

    verify(busStopFavoriteRepository).getBusStopFavorites()
  }
}
