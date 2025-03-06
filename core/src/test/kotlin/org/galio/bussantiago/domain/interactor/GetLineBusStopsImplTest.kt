package org.galio.bussantiago.domain.interactor

import org.galio.bussantiago.Either
import org.galio.bussantiago.core.GetLineBusStops
import org.galio.bussantiago.domain.model.BusStop
import org.galio.bussantiago.domain.model.LineDetails
import org.galio.bussantiago.domain.model.Route
import org.galio.bussantiago.domain.repository.LineDetailsRepository
import org.galio.bussantiago.exception.ServiceException
import org.galio.bussantiago.util.mock
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.mockito.Mockito.verify

class GetLineBusStopsImplTest {

  private val lineDetailsRepository = Mockito.mock(LineDetailsRepository::class.java)
  private val getLineBusStops = GetLineBusStopsImpl(lineDetailsRepository)

  @Test
  fun `invokes repository and returns the bus stops of the correct route`() {
    val request = GetLineBusStops.Request(123, "Route 1")
    val route1 = createRoute("Route 1", mock())
    val route2 = createRoute("Route 2", mock())
    val lineDetailsStub = createLineDetails(listOf(route1, route2))
    given(lineDetailsRepository.getLineDetails(request.lineId))
      .willReturn(Either.Right(lineDetailsStub))

    val result = getLineBusStops(request)

    verify(lineDetailsRepository).getLineDetails(request.lineId)
    assertTrue(result.isRight)
    assertEquals(route1.busStops, result.rightValue)
  }

  @Test
  fun `if the repository fails, returns the exception received`() {
    val request = GetLineBusStops.Request(123, "Any name")
    val exception = ServiceException()
    given(lineDetailsRepository.getLineDetails(request.lineId))
      .willReturn(Either.Left(exception))

    val result = getLineBusStops(request)

    verify(lineDetailsRepository).getLineDetails(request.lineId)
    assertTrue(result.isLeft)
    assertEquals(exception, result.leftValue)
  }

  private fun createRoute(name: String, busStops: List<BusStop>): Route {
    return Route(
      name = name,
      direction = "Any direction",
      busStops = busStops
    )
  }

  private fun createLineDetails(routes: List<Route>): LineDetails {
    return LineDetails(
      id = 1,
      code = "Any code",
      synoptic = "Any Synoptic",
      name = "Any name",
      information = "Any information",
      style = "Any color",
      routes = routes,
      incidences = emptyList()
    )
  }
}
