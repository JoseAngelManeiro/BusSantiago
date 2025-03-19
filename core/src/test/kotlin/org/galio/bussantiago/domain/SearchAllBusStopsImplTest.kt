package org.galio.bussantiago.domain

import org.galio.bussantiago.core.Either
import org.galio.bussantiago.core.model.BusStopSearch
import org.galio.bussantiago.data.exception.ServiceException
import org.galio.bussantiago.data.repository.SearchBusStopRepository
import org.galio.bussantiago.util.mock
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.BDDMockito.given

class SearchAllBusStopsImplTest {

  private val searchBusStopRepository = mock<SearchBusStopRepository>()
  private val searchAllBusStops = SearchAllBusStopsImpl(searchBusStopRepository)

  @Test
  fun `invokes repository and returns the list received`() {
    val busStops = listOf(mock<BusStopSearch>())
    given(searchBusStopRepository.searchAllBusStops()).willReturn(Either.right(busStops))

    val result = searchAllBusStops(Unit)

    assertTrue(result.isRight)
    assertEquals(busStops, result.rightValue)
  }

  @Test
  fun `if the repository fails, returns the exception received`() {
    val exception = ServiceException()
    given(searchBusStopRepository.searchAllBusStops()).willReturn(Either.left(exception))

    val result = searchAllBusStops(Unit)

    assertTrue(result.isLeft)
    assertEquals(exception, result.leftValue)
  }
}
