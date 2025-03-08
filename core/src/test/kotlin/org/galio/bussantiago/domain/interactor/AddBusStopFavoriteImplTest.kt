package org.galio.bussantiago.domain.interactor

import org.galio.bussantiago.data.repository.BusStopFavoriteRepository
import org.galio.bussantiago.domain.model.BusStopFavorite
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify

class AddBusStopFavoriteImplTest {

  private val busStopFavoriteRepository = Mockito.mock(BusStopFavoriteRepository::class.java)
  private val addBusStopFavorite = AddBusStopFavoriteImpl(busStopFavoriteRepository)

  @Test
  fun `invokes the repository with the correct request`() {
    val request = BusStopFavorite("1234", "Name")

    addBusStopFavorite(request)

    verify(busStopFavoriteRepository).add(request)
  }
}
