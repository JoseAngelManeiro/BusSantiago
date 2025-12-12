package org.galio.bussantiago.domain

import org.galio.bussantiago.core.model.BusStopSearch
import org.galio.bussantiago.data.exception.ServiceException
import org.galio.bussantiago.data.repository.SearchBusStopRepository
import org.galio.bussantiago.util.mock
import org.galio.bussantiago.util.thenFailure
import org.galio.bussantiago.util.thenSuccess
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.whenever

class SearchAllBusStopsImplTest {

  private val searchBusStopRepository = mock<SearchBusStopRepository>()
  private val searchAllBusStops = SearchAllBusStopsImpl(searchBusStopRepository)

  @Test
  fun `invokes repository and returns the list received`() {
    val busStops = listOf(mock<BusStopSearch>())
    whenever(searchBusStopRepository.searchAllBusStops()).thenSuccess(busStops)

    val result = searchAllBusStops()

    assertTrue(result.isSuccess)
    assertEquals(busStops, result.getOrNull())
  }

  @Test
  fun `if the repository fails, returns the exception received`() {
    val exception = ServiceException()
    whenever(searchBusStopRepository.searchAllBusStops()).thenFailure(exception)

    val result = searchAllBusStops()

    assertTrue(result.isFailure)
    assertEquals(exception, result.exceptionOrNull())
  }
}
