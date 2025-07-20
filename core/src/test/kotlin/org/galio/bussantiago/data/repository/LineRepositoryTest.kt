package org.galio.bussantiago.data.repository

import org.galio.bussantiago.core.model.Line
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.cache.LineCache
import org.galio.bussantiago.data.entity.LineEntity
import org.galio.bussantiago.data.exception.ServiceException
import org.galio.bussantiago.data.mapper.LineMapper
import org.galio.bussantiago.util.mock
import org.galio.bussantiago.util.thenFailure
import org.galio.bussantiago.util.thenSuccess
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class LineRepositoryTest {

  private val apiClient = mock<ApiClient>()
  private val mapper = mock<LineMapper>()
  private val cache = mock<LineCache>()

  private val repository = LineRepository(apiClient, mapper, cache)

  @Test
  fun `when cache data is valid should return that data directly`() {
    val line = mock<Line>()
    val lines = listOf(line)
    whenever(cache.getAll()).thenSuccess(lines)

    val result = repository.getLines()

    assertEquals(lines, result.getOrNull())
  }

  @Test
  fun `when cache data is not valid should get data from service and save it in cache`() {
    val line = mock<Line>()
    val lineEntity = mock<LineEntity>()
    val lines = listOf(line)
    val lineEntities = listOf(lineEntity)
    whenever(cache.getAll()).thenFailure(mock())
    whenever(apiClient.getLines()).thenSuccess(lineEntities)
    whenever(mapper.toDomain(lineEntity)).thenReturn(line)

    val result = repository.getLines()

    verify(cache).save(lines)
    assertEquals(lines, result.getOrNull())
  }

  @Test
  fun `when cache data is not valid and service fails should return the service exception`() {
    val exception = ServiceException()
    whenever(cache.getAll()).thenFailure(mock())
    whenever(apiClient.getLines()).thenFailure(exception)

    val result = repository.getLines()

    assertEquals(exception, result.exceptionOrNull())
  }
}
