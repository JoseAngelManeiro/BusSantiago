package org.galio.bussantiago.domain

import org.galio.bussantiago.data.repository.BusStopRemainingTimesRepository
import org.galio.bussantiago.util.mock
import org.junit.Test
import org.mockito.Mockito.verify

class GetBusStopRemainingTimesImplTest {

  private val busStopRemainingTimesRepository = mock<BusStopRemainingTimesRepository>()
  private val getBusStopRemainingTimes =
    GetBusStopRemainingTimesImpl(busStopRemainingTimesRepository)

  @Test
  fun `invokes the repository with the correct request`() {
    val busStopCode = "1234"

    getBusStopRemainingTimes(busStopCode)

    verify(busStopRemainingTimesRepository).getBusStopRemainingTimes(busStopCode)
  }
}
