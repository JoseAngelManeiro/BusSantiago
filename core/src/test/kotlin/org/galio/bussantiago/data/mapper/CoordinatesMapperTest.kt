package org.galio.bussantiago.data.mapper

import org.galio.bussantiago.core.model.Coordinates
import org.galio.bussantiago.data.entity.CoordinatesEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class CoordinatesMapperTest {

  private val coordinatesMapper = CoordinatesMapper()

  @Test
  fun `should map the entity to the domain model expected`() {
    val coordinatesEntity = CoordinatesEntity(latitud = 4.55, longitud = -8.66)
    val coordinates = Coordinates(latitude = 4.55, longitude = -8.66)

    val result = coordinatesMapper.toDomain(coordinatesEntity)

    assertEquals(coordinates, result)
  }
}
