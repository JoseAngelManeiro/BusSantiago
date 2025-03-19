package org.galio.bussantiago.domain

import org.galio.bussantiago.core.model.BusStopFavorite
import org.galio.bussantiago.data.repository.BusStopFavoriteRepository
import org.junit.Test
import org.mockito.Mockito

class RemoveBusStopFavoriteImplTest {

  private val busStopFavoriteRepository = Mockito.mock(BusStopFavoriteRepository::class.java)
  private val removeBusStopFavorite = RemoveBusStopFavoriteImpl(busStopFavoriteRepository)

  @Test
  fun `invokes the repository with the correct request`() {
    val request = BusStopFavorite("1234", "Name")

    removeBusStopFavorite(request)

    Mockito.verify(busStopFavoriteRepository).remove(request)
  }
}
