package org.galio.bussantiago.domain.interactor

import org.galio.bussantiago.domain.repository.BusStopRemainingTimesRepository
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify

class GetBusStopRemainingTimesTest {

    private val busStopRemainingTimesRepository =
      Mockito.mock(BusStopRemainingTimesRepository::class.java)
    private val getBusStopRemainingTimes =
      GetBusStopRemainingTimes(
        busStopRemainingTimesRepository
      )

    @Test
    fun `invokes the repository with the correct request`() {
        val busStopCode = "1234"

        getBusStopRemainingTimes(busStopCode)

        verify(busStopRemainingTimesRepository).getBusStopRemainingTimes(busStopCode)
    }
}
