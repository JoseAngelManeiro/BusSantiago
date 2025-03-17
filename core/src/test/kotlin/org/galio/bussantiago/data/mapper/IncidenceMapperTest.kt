package org.galio.bussantiago.data.mapper

import org.galio.bussantiago.core.model.Incidence
import org.galio.bussantiago.data.entity.IncidenceEntity
import org.galio.bussantiago.util.mock
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.whenever
import java.util.Date

class IncidenceMapperTest {

  private val dateMapper = mock<DateMapper>()
  private val incidenceMapper = IncidenceMapper(dateMapper)

  @Test
  fun `should map the entity to the domain model expected`() {
    val startDate = mock<Date>()
    val endDate = mock<Date>()
    val incidenceEntity = IncidenceEntity(
      id = 1,
      titulo = "Any title",
      descripcion = "Any description",
      inicio = "2025-03-14 17:55",
      fin = "2025-04-15 18:00"
    )
    val incidenceExpected = Incidence(
      id = 1,
      title = "Any title",
      description = "Any description",
      startDate = startDate,
      endDate = endDate
    )
    whenever(dateMapper.getDate("2025-03-14 17:55")).thenReturn(startDate)
    whenever(dateMapper.getDate("2025-04-15 18:00")).thenReturn(endDate)

    val result = incidenceMapper.toDomain(incidenceEntity)

    assertEquals(incidenceExpected, result)
  }
}
