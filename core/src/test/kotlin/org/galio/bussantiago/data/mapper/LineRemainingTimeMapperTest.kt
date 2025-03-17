package org.galio.bussantiago.data.mapper

import org.galio.bussantiago.core.model.LineRemainingTime
import org.galio.bussantiago.data.entity.LineRemainingTimeEntity
import org.galio.bussantiago.util.mock
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.whenever
import java.util.Date

class LineRemainingTimeMapperTest {

  private val dateMapper = mock<DateMapper>()
  private val lineRemainingTimeMapper = LineRemainingTimeMapper(dateMapper)

  @Test
  fun `should map the entity to the domain model expected`() {
    val date = mock<Date>()
    val lineRemainingTimeEntity = LineRemainingTimeEntity(
      id = 1,
      sinoptico = "Any sinoptico",
      nombre = "Any name",
      estilo = "Any style",
      proximoPaso = "2025-03-14 17:55",
      minutosProximoPaso = 3
    )
    val lineRemainingTime = LineRemainingTime(
      id = 1,
      synoptic = "Any sinoptico",
      name = "Any name",
      style = "Any style",
      nextArrival = date,
      minutesUntilNextArrival = 3
    )
    whenever(dateMapper.getDate("2025-03-14 17:55")).thenReturn(date)

    val result = lineRemainingTimeMapper.toDomain(lineRemainingTimeEntity)

    assertEquals(lineRemainingTime, result)
  }
}
