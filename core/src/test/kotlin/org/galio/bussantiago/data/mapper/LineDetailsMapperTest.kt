package org.galio.bussantiago.data.mapper

import org.galio.bussantiago.core.model.Incidence
import org.galio.bussantiago.core.model.LineDetails
import org.galio.bussantiago.core.model.Route
import org.galio.bussantiago.data.entity.IncidenceEntity
import org.galio.bussantiago.data.entity.LineDetailsEntity
import org.galio.bussantiago.data.entity.RouteEntity
import org.galio.bussantiago.util.mock
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.whenever

class LineDetailsMapperTest {

  private val routeMapper = mock<RouteMapper>()
  private val incidenceMapper = mock<IncidenceMapper>()
  private val lineDetailsMapper = LineDetailsMapper(routeMapper, incidenceMapper)

  @Test
  fun `should map the entity to the domain model expected`() {
    val routeEntity = mock<RouteEntity>()
    val incidenceEntity = mock<IncidenceEntity>()
    val route = mock<Route>()
    val incidence = mock<Incidence>()
    val lineDetailsEntity = LineDetailsEntity(
      id = 15,
      codigo = "15",
      sinoptico = "L15",
      nombre = "Campus Norte / Campus Sur",
      informacion = "Información",
      estilo = "#7a4b2a",
      trayectos = listOf(routeEntity),
      incidencias = listOf(incidenceEntity)
    )
    val lineDetailsExpected = LineDetails(
      id = 15,
      code = "15",
      synoptic = "L15",
      name = "Campus Norte / Campus Sur",
      information = "Información",
      style = "#7a4b2a",
      routes = listOf(route),
      incidences = listOf(incidence)
    )
    whenever(routeMapper.toDomain(routeEntity)).thenReturn(route)
    whenever(incidenceMapper.toDomain(incidenceEntity)).thenReturn(incidence)

    val result = lineDetailsMapper.toDomain(lineDetailsEntity)

    assertEquals(lineDetailsExpected, result)
  }
}
