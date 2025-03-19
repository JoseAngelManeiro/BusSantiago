package org.galio.bussantiago.data.mapper

import org.galio.bussantiago.core.model.BusStopRemainingTimes
import org.galio.bussantiago.core.model.Coordinates
import org.galio.bussantiago.core.model.LineRemainingTime
import org.galio.bussantiago.data.entity.BusStopRemainingTimesEntity
import org.galio.bussantiago.data.entity.CoordinatesEntity
import org.galio.bussantiago.data.entity.LineRemainingTimeEntity
import org.galio.bussantiago.util.mock
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.whenever

class BusStopRemainingTimesMapperTest {

  private val coordinatesMapper = mock<CoordinatesMapper>()
  private val lineRemainingTimeMapper = mock<LineRemainingTimeMapper>()
  private val busStopRemainingTimesMapper =
    BusStopRemainingTimesMapper(coordinatesMapper, lineRemainingTimeMapper)

  @Test
  fun `should map the entity to the domain model expected`() {
    val coordinatesEntity = mock<CoordinatesEntity>()
    val lineRemainingTimeEntity = mock<LineRemainingTimeEntity>()
    val coordinates = mock<Coordinates>()
    val lineRemainingTime = mock<LineRemainingTime>()
    val busStopRemainingTimesEntity = BusStopRemainingTimesEntity(
      id = 1,
      codigo = "1",
      nombre = "Any name",
      zona = "Any zone",
      coordenadas = coordinatesEntity,
      lineas = listOf(lineRemainingTimeEntity)
    )
    val busStopRemainingTimesExpected = BusStopRemainingTimes(
      id = 1,
      code = "1",
      name = "Any name",
      zone = "Any zone",
      coordinates = coordinates,
      lineRemainingTimes = listOf(lineRemainingTime)
    )
    whenever(coordinatesMapper.toDomain(coordinatesEntity))
      .thenReturn(coordinates)
    whenever(lineRemainingTimeMapper.toDomain(lineRemainingTimeEntity))
      .thenReturn(lineRemainingTime)

    val result = busStopRemainingTimesMapper.toDomain(busStopRemainingTimesEntity)

    assertEquals(busStopRemainingTimesExpected, result)
  }
}
