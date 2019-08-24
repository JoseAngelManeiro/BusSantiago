package org.galio.bussantiago.features.times

import org.galio.bussantiago.domain.model.BusStopFavorite
import org.galio.bussantiago.domain.repository.BusStopFavoriteRepository
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify

class AddBusStopFavoriteTest {

    private val busStopFavoriteRepository = Mockito.mock(BusStopFavoriteRepository::class.java)
    private val addBusStopFavorite = AddBusStopFavorite(busStopFavoriteRepository)

    @Test
    fun `invokes the repository with the correct request`() {
        val request = BusStopFavorite("1234", "Name")

        addBusStopFavorite(request)

        verify(busStopFavoriteRepository).add(request)
    }
}
