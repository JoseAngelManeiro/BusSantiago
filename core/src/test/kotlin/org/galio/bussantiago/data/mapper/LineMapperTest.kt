package org.galio.bussantiago.data.mapper

import org.galio.bussantiago.core.model.Line
import org.galio.bussantiago.data.entity.LineEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class LineMapperTest {

  private val lineMapper = LineMapper()

  @Test
  fun `should map the entity to the domain model expected`() {
    val lineEntity = LineEntity(
      id = 1,
      codigo = "1",
      sinoptico = "L1",
      nombre = "Cemiterio de Boisaca / hospital Clínico",
      empresa = "TRALUSA",
      incidencias = 1,
      estilo = "#f32621"
    )
    val line = Line(
      id = 1,
      code = "1",
      synoptic = "L1",
      name = "Cemiterio de Boisaca / hospital Clínico",
      company = "TRALUSA",
      incidents = 1,
      style = "#f32621"
    )

    val result = lineMapper.toDomain(lineEntity)

    assertEquals(line, result)
  }
}
