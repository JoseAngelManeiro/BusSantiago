package org.galio.bussantiago.data.repository

import org.galio.bussantiago.core.Either
import org.galio.bussantiago.core.model.Line
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.cache.LineCache
import org.galio.bussantiago.data.entity.LineEntity
import org.galio.bussantiago.data.mapper.LineMapper
import org.galio.bussantiago.util.mock
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.BDDMockito.given

class LineRepositoryTest {

  private val apiClient = mock<ApiClient>()
  private val mapper = mock<LineMapper>()
  private val cache = LineCache()

  private val repository = LineRepository(apiClient, mapper, cache)

  @Test
  fun `when cache data is valid should return that data directly`() {
    val line = mock<Line>()
    val lines = listOf(line)
    cache.save(lines)

    val result = repository.getLines()

    assertEquals(lines, result.successValue)
  }

  @Test
  fun `when cache data is not valid should get data from service and save it in cache`() {
    val line = mock<Line>()
    val lineEntity = mock<LineEntity>()
    val lines = listOf(line)
    val lineEntities = listOf(lineEntity)
    given(apiClient.getLines()).willReturn(Either.success(lineEntities))
    given(mapper.toDomain(lineEntity)).willReturn(line)

    val result = repository.getLines()

    assertEquals(lines, cache.getAll())
    assertEquals(lines, result.successValue)
  }

  @Test
  fun `when cache data is not valid and service fails should return the exception`() {
    val exception = mock<Exception>()
    given(apiClient.getLines()).willReturn(Either.error(exception))

    val result = repository.getLines()

    assertEquals(exception, result.errorValue)
  }
}
