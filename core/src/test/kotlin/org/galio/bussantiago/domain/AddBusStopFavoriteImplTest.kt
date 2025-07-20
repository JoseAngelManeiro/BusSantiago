package org.galio.bussantiago.domain

import org.galio.bussantiago.core.model.BusStopFavorite
import org.galio.bussantiago.data.repository.BusStopFavoriteRepository
import org.galio.bussantiago.util.mock
import org.junit.Test
import org.mockito.Mockito.verify

class AddBusStopFavoriteImplTest {

  private val busStopFavoriteRepository = mock<BusStopFavoriteRepository>()
  private val addBusStopFavorite = AddBusStopFavoriteImpl(busStopFavoriteRepository)

  @Test
  fun `invokes the repository with the correct request`() {
    val request = BusStopFavorite("1234", "Name")

    addBusStopFavorite(request)

    verify(busStopFavoriteRepository).add(request)
  }
}
