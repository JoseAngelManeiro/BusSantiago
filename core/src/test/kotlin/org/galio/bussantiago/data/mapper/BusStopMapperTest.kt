package org.galio.bussantiago.data.mapper

import org.galio.bussantiago.core.model.BusStop
import org.galio.bussantiago.core.model.Coordinates
import org.galio.bussantiago.data.entity.BusStopEntity
import org.galio.bussantiago.data.entity.CoordinatesEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class BusStopMapperTest {

  private val busStopMapper = BusStopMapper(CoordinatesMapper())

  @Test
  fun `should map the entity to the domain model expected`() {
    val busStopEntity = BusStopEntity(
      id = 1,
      codigo = "1",
      nombre = "Any name",
      zona = null,
      extraordinaria = true,
      coordenadas = CoordinatesEntity(latitud = 4.55, longitud = -8.66)
    )
    val busStop = BusStop(
      id = 1,
      code = "1",
      name = "Any name",
      zone = null,
      extraordinary = true,
      coordinates = Coordinates(latitude = 4.55, longitude = -8.66)
    )

    val result = busStopMapper.toDomain(busStopEntity)

    assertEquals(busStop, result)
  }
}
