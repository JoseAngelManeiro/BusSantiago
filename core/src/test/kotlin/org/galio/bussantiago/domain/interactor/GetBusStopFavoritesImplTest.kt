package org.galio.bussantiago.domain.interactor

import org.galio.bussantiago.domain.repository.BusStopFavoriteRepository
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
