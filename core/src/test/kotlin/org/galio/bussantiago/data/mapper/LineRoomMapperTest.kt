package org.galio.bussantiago.data.mapper

import org.galio.bussantiago.core.model.Line
import org.galio.bussantiago.data.local.room.entity.LineEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class LineRoomMapperTest {

  private val mapper = LineRoomMapper()

  @Test
  fun `given LineEntity when mapped should return Line domain model`() {
    val dataModel = LineEntity(
      id = 1,
      code = "6",
      synoptic = "6",
      name = "Os Tilos",
      company = "Tussa",
      incidents = 0,
      style = "color: #FF0000"
    )

    val expectedDomainModel = Line(
      id = 1,
      code = "6",
      synoptic = "6",
      name = "Os Tilos",
      company = "Tussa",
      incidents = 0,
      style = "color: #FF0000"
    )

    val actualDomainModel = mapper.toDomain(dataModel)

    assertEquals(expectedDomainModel, actualDomainModel)
  }

  @Test
  fun `given Line domain model when mapped should return LineEntity`() {
    val domainModel = Line(
      id = 1,
      code = "6",
      synoptic = "6",
      name = "Os Tilos",
      company = "Tussa",
      incidents = 0,
      style = "color: #FF0000"
    )

    val expectedDataModel = LineEntity(
      id = 1,
      code = "6",
      synoptic = "6",
      name = "Os Tilos",
      company = "Tussa",
      incidents = 0,
      style = "color: #FF0000"
    )

    val actualDataModel = mapper.toEntity(domainModel)

    assertEquals(expectedDataModel, actualDataModel)
  }
}
