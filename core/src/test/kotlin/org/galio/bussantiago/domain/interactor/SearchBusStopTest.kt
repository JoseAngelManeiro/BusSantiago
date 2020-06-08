package org.galio.bussantiago.domain.interactor

import org.galio.bussantiago.Either
import org.galio.bussantiago.domain.model.Coordinates
import org.galio.bussantiago.domain.model.BusStopSearch
import org.galio.bussantiago.domain.repository.SearchBusStopRepository
import org.galio.bussantiago.exception.ServiceException
import org.galio.bussantiago.util.mock
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify

class SearchBusStopTest {

  private val searchBusStopRepository = mock<SearchBusStopRepository>()
  private val getSearchBusStop = SearchBusStop(searchBusStopRepository)

  private val request = "45"

  @Test
  fun `invokes repository and returns the one with the correct code`() {
    val listStub = listOf(
      createSearchBusStop(code = "45"),
      createSearchBusStop(code = "223"),
      createSearchBusStop(code = "1")
    )
    given(searchBusStopRepository.getSearchBusStops(request))
      .willReturn(Either.right(listStub))

    val result = getSearchBusStop(request)

    verify(searchBusStopRepository).getSearchBusStops(request)
    assertTrue(result.isRight)
    assertEquals(request, result.rightValue?.code)
  }

  @Test
  fun `if the searched one doesn't exist, it returns null`() {
    val listStub = listOf(
      createSearchBusStop(code = "223"),
      createSearchBusStop(code = "1")
    )
    given(searchBusStopRepository.getSearchBusStops(request))
      .willReturn(Either.right(listStub))

    val result = getSearchBusStop(request)

    verify(searchBusStopRepository).getSearchBusStops(request)
    assertTrue(result.isRight)
    assertNull(result.rightValue)
  }

  @Test
  fun `if the repository fails, returns the exception received`() {
    val exception = ServiceException()
    given(searchBusStopRepository.getSearchBusStops(request))
      .willReturn(Either.left(exception))

    val result = getSearchBusStop(request)

    verify(searchBusStopRepository).getSearchBusStops(request)
    assertTrue(result.isLeft)
    assertEquals(exception, result.leftValue)
  }

  private fun createSearchBusStop(code: String): BusStopSearch {
    return BusStopSearch(
      id = 1,
      code = code,
      name = "Any name",
      zone = null,
      coordinates = Coordinates(44.4, -22.2),
      lines = mock()
    )
  }
}
