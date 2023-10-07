package org.galio.bussantiago.features.stops.map

import org.galio.bussantiago.domain.model.BusStop
import org.galio.bussantiago.domain.model.Coordinates
import org.galio.bussantiago.domain.model.LineDetails
import org.galio.bussantiago.domain.model.Route
import org.junit.Assert.assertEquals
import org.junit.Test

class LineMapModelFactoryTest {

  private val lineMapModelFactory = LineMapModelFactory()

  @Test
  fun `should filter the bus stops by route`() {
    val route1 = createRoute(
      "Route 1",
      listOf(createBusStop("657"), createBusStop("210"))
    )
    val route2 = createRoute(
      "Route 2",
      listOf(createBusStop("502"))
    )
    val lineDetails = createLineDetails(listOf(route1, route2))

    val result = lineMapModelFactory.createLineMapModelFactory(
      routeName = "Route 1",
      lineDetails = lineDetails
    )

    assertEquals(2, result.busStopMapModels.size)
    assertEquals("657", result.busStopMapModels[0].code)
    assertEquals("210", result.busStopMapModels[1].code)
  }

  private fun createBusStop(code: String): BusStop {
    return BusStop(
      id = 1,
      code = code,
      name = "Any name",
      zone = null,
      extraordinary = true,
      coordinates = Coordinates(42.8835803282227, -8.54145169258118)
    )
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
      style = "Any style",
      routes = routes,
      incidences = emptyList()
    )
  }
}
