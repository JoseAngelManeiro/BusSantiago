package org.galio.bussantiago.data.mapper

import org.galio.bussantiago.core.model.LineDetails
import org.galio.bussantiago.data.local.room.entity.LineDetailsEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class LineDetailsRoomMapperTest {

  private val mapper = LineDetailsRoomMapper()

  @Test
  fun `given LineDetailsEntity when mapped should return LineDetails domain model`() {
    val routesJson = "[]"
    
    val dataModel = LineDetailsEntity(
      id = 1,
      code = "6",
      synoptic = "6",
      name = "Os Tilos",
      information = "Info",
      style = "color: #FF0000",
      routesJson = routesJson
    )

    val expectedDomainModel = LineDetails(
      id = 1,
      code = "6",
      synoptic = "6",
      name = "Os Tilos",
      information = "Info",
      style = "color: #FF0000",
      routes = emptyList(),
      incidences = emptyList()
    )

    val actualDomainModel = mapper.toDomain(dataModel)

    assertEquals(expectedDomainModel, actualDomainModel)
  }

  @Test
  fun `given LineDetailsEntity with corrupted JSON when mapped should return LineDetails with empty routes`() {
    val dataModel = LineDetailsEntity(
      id = 1,
      code = "6",
      synoptic = "6",
      name = "Os Tilos",
      information = "Info",
      style = "color: #FF0000",
      routesJson = "invalid_json{"
    )

    val expectedDomainModel = LineDetails(
      id = 1,
      code = "6",
      synoptic = "6",
      name = "Os Tilos",
      information = "Info",
      style = "color: #FF0000",
      routes = emptyList(),
      incidences = emptyList()
    )

    val actualDomainModel = mapper.toDomain(dataModel)

    assertEquals(expectedDomainModel, actualDomainModel)
  }

  @Test
  fun `given LineDetails domain model when mapped should return LineDetailsEntity`() {
    val domainModel = LineDetails(
      id = 1,
      code = "6",
      synoptic = "6",
      name = "Os Tilos",
      information = "Info",
      style = "color: #FF0000",
      routes = emptyList(),
      incidences = emptyList() // Should be ignored in Room entity
    )

    val expectedDataModel = LineDetailsEntity(
      id = 1,
      code = "6",
      synoptic = "6",
      name = "Os Tilos",
      information = "Info",
      style = "color: #FF0000",
      routesJson = "[]"
    )

    val actualDataModel = mapper.toEntity(domainModel)

    assertEquals(expectedDataModel, actualDataModel)
  }
}
