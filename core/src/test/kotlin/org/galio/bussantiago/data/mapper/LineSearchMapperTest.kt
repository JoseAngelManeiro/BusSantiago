package org.galio.bussantiago.data.mapper

import org.galio.bussantiago.core.model.LineSearch
import org.galio.bussantiago.data.entity.LineSearchEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class LineSearchMapperTest {

  private val lineSearchMapper = LineSearchMapper()

  @Test
  fun `should map the entity to the domain model expected`() {
    val lineSearchEntity = LineSearchEntity(sinoptico = "Any sinoptico", estilo = "Any estilo")
    val lineSearch = LineSearch(synoptic = "Any sinoptico", style = "Any estilo")

    val result = lineSearchMapper.toDomain(lineSearchEntity)

    assertEquals(lineSearch, result)
  }
}
