package org.galio.bussantiago.features.times

import org.galio.bussantiago.domain.model.BusStopFavorite
import org.galio.bussantiago.domain.repository.BusStopFavoriteRepository
import org.junit.Test
import org.mockito.Mockito

class RemoveBusStopFavoriteTest {

    private val busStopFavoriteRepository = Mockito.mock(BusStopFavoriteRepository::class.java)
    private val removeBusStopFavorite = RemoveBusStopFavorite(busStopFavoriteRepository)

    @Test
    fun `invokes the repository with the correct request`() {
        val request = BusStopFavorite("1234", "Name")

        removeBusStopFavorite(request)

        Mockito.verify(busStopFavoriteRepository).remove(request)
    }
}
