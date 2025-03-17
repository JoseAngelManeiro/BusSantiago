package org.galio.bussantiago.data.mapper

import org.galio.bussantiago.core.model.BusStop
import org.galio.bussantiago.core.model.Route
import org.galio.bussantiago.data.entity.BusStopEntity
import org.galio.bussantiago.data.entity.RouteEntity
import org.galio.bussantiago.util.mock
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.whenever

class RouteMapperTest {

  private val busStopMapper = mock<BusStopMapper>()
  private val routeMapper = RouteMapper(busStopMapper)

  @Test
  fun `should map the entity to the domain model expected`() {
    val busStopEntity = mock<BusStopEntity>()
    val busStop = mock<BusStop>()
    val routeEntity = RouteEntity(
      nombre = "Any name",
      sentido = "Any direction",
      paradas = listOf(busStopEntity)
    )
    val routeExpected = Route(
      name = "Any name",
      direction = "Any direction",
      busStops = listOf(busStop)
    )
    whenever(busStopMapper.toDomain(busStopEntity)).thenReturn(busStop)

    val result = routeMapper.toDomain(routeEntity)

    assertEquals(routeExpected, result)
  }
}
