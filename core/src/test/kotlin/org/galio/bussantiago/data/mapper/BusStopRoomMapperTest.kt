package org.galio.bussantiago.data.mapper

import org.galio.bussantiago.core.model.BusStopSearch
import org.galio.bussantiago.core.model.Coordinates
import org.galio.bussantiago.core.model.LineSearch
import org.galio.bussantiago.data.local.room.entity.BusStopEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class BusStopRoomMapperTest {

  private val mapper = BusStopRoomMapper()

  @Test
  fun `given BusStopEntity when mapped should return BusStopSearch domain model`() {
    val linesJson = "[{\"synoptic\":\"6\",\"style\":\"color: #FF0000\"}]"
    
    val dataModel = BusStopEntity(
      id = 1,
      code = "123",
      name = "Praza de Galicia",
      zone = "Centro",
      latitude = 42.0,
      longitude = -8.0,
      linesJson = linesJson
    )

    val expectedDomainModel = BusStopSearch(
      id = 1,
      code = "123",
      name = "Praza de Galicia",
      zone = "Centro",
      coordinates = Coordinates(42.0, -8.0),
      lines = listOf(LineSearch("6", "color: #FF0000"))
    )

    val actualDomainModel = mapper.toDomain(dataModel)

    assertEquals(expectedDomainModel, actualDomainModel)
  }

  @Test
  fun `given BusStopEntity with corrupted JSON when mapped should return BusStopSearch with empty lines`() {
    val dataModel = BusStopEntity(
      id = 1,
      code = "123",
      name = "Praza de Galicia",
      zone = "Centro",
      latitude = 42.0,
      longitude = -8.0,
      linesJson = "invalid_json_string{"
    )

    val expectedDomainModel = BusStopSearch(
      id = 1,
      code = "123",
      name = "Praza de Galicia",
      zone = "Centro",
      coordinates = Coordinates(42.0, -8.0),
      lines = emptyList() // Should fallback to empty list instead of crashing
    )

    val actualDomainModel = mapper.toDomain(dataModel)

    assertEquals(expectedDomainModel, actualDomainModel)
  }

  @Test
  fun `given BusStopSearch domain model when mapped should return BusStopEntity`() {
    val domainModel = BusStopSearch(
      id = 1,
      code = "123",
      name = "Praza de Galicia",
      zone = "Centro",
      coordinates = Coordinates(42.0, -8.0),
      lines = listOf(LineSearch("6", "color: #FF0000"))
    )

    val expectedDataModel = BusStopEntity(
      id = 1,
      code = "123",
      name = "Praza de Galicia",
      zone = "Centro",
      latitude = 42.0,
      longitude = -8.0,
      linesJson = "[{\"synoptic\":\"6\",\"style\":\"color: #FF0000\"}]"
    )

    val actualDataModel = mapper.toEntity(domainModel)

    assertEquals(expectedDataModel, actualDataModel)
  }
}
