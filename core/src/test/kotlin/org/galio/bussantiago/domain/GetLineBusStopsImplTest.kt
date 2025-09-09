package org.galio.bussantiago.domain

import org.galio.bussantiago.core.GetLineBusStops
import org.galio.bussantiago.core.model.BusStop
import org.galio.bussantiago.core.model.LineDetails
import org.galio.bussantiago.core.model.Route
import org.galio.bussantiago.data.exception.ServiceException
import org.galio.bussantiago.data.repository.LineDetailsRepository
import org.galio.bussantiago.util.mock
import org.galio.bussantiago.util.thenFailure
import org.galio.bussantiago.util.thenSuccess
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever

class GetLineBusStopsImplTest {

  private val lineDetailsRepository = mock<LineDetailsRepository>()
  private val getLineBusStops = GetLineBusStopsImpl(lineDetailsRepository)

  @Test
  fun `invokes repository and returns the bus stops of the correct route`() {
    val request = GetLineBusStops.Request(123, "Route 1")
    val route1 = createRoute("Route 1", mock())
    val route2 = createRoute("Route 2", mock())
    val lineDetailsStub = createLineDetails(listOf(route1, route2))
    whenever(lineDetailsRepository.getLineDetails(request.lineId)).thenSuccess(lineDetailsStub)

    val result = getLineBusStops(request)

    verify(lineDetailsRepository).getLineDetails(request.lineId)
    assertTrue(result.isSuccess)
    assertEquals(route1.busStops, result.getOrNull())
  }

  @Test
  fun `if the repository fails, returns the exception received`() {
    val request = GetLineBusStops.Request(123, "Any name")
    val exception = ServiceException()
    whenever(lineDetailsRepository.getLineDetails(request.lineId)).thenFailure(exception)

    val result = getLineBusStops(request)

    verify(lineDetailsRepository).getLineDetails(request.lineId)
    assertTrue(result.isFailure)
    assertEquals(exception, result.exceptionOrNull())
  }

  @Test
  fun `if the route requested does not exist, returns an empty list`() {
    val request = GetLineBusStops.Request(123, "Unknown Route")
    val route1 = createRoute("Route 1", mock())
    val route2 = createRoute("Route 2", mock())
    val lineDetailsStub = createLineDetails(listOf(route1, route2))
    whenever(lineDetailsRepository.getLineDetails(request.lineId)).thenSuccess(lineDetailsStub)

    val result = getLineBusStops(request)

    verify(lineDetailsRepository).getLineDetails(request.lineId)
    assertTrue(result.isSuccess)
    assertEquals(emptyList<BusStop>(), result.getOrNull())
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
