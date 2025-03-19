package org.galio.bussantiago.data.mapper

import org.galio.bussantiago.core.model.BusStopSearch
import org.galio.bussantiago.core.model.Coordinates
import org.galio.bussantiago.core.model.LineSearch
import org.galio.bussantiago.data.entity.BusStopSearchEntity
import org.galio.bussantiago.data.entity.CoordinatesEntity
import org.galio.bussantiago.data.entity.LineSearchEntity
import org.galio.bussantiago.util.mock
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.whenever

class BusStopSearchMapperTest {

  private val coordinatesMapper = mock<CoordinatesMapper>()
  private val lineSearchMapper = mock<LineSearchMapper>()
  private val busStopSearchMapper = BusStopSearchMapper(coordinatesMapper, lineSearchMapper)

  @Test
  fun `should map the entity to the domain model expected`() {
    val coordinatesEntity = mock<CoordinatesEntity>()
    val lineSearchEntity = mock<LineSearchEntity>()
    val coordinates = mock<Coordinates>()
    val lineSearch = mock<LineSearch>()
    val busStopSearchEntity = BusStopSearchEntity(
      id = 15,
      codigo = "015",
      nombre = "Any name",
      zona = "Any zone",
      coordenadas = coordinatesEntity,
      lineas = listOf(lineSearchEntity)
    )
    val busStopSearchExpected = BusStopSearch(
      id = 15,
      code = "15",
      name = "Any name",
      zone = "Any zone",
      coordinates = coordinates,
      lines = listOf(lineSearch)
    )
    whenever(coordinatesMapper.toDomain(coordinatesEntity)).thenReturn(coordinates)
    whenever(lineSearchMapper.toDomain(lineSearchEntity)).thenReturn(lineSearch)

    val result = busStopSearchMapper.toDomain(busStopSearchEntity)

    assertEquals(busStopSearchExpected, result)
  }
}
