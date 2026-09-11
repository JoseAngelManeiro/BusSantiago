package org.galio.bussantiago.data.repository

import org.galio.bussantiago.core.model.Line
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.cache.LineCache
import org.galio.bussantiago.data.entity.LineEntity
import org.galio.bussantiago.data.exception.ServiceException
import org.galio.bussantiago.data.local.room.LineDao
import org.galio.bussantiago.data.mapper.LineMapper
import org.galio.bussantiago.util.mock
import org.galio.bussantiago.util.thenFailure
import org.galio.bussantiago.util.thenSuccess
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

import org.galio.bussantiago.data.mapper.LineRoomMapper

class LineRepositoryTest {

  private val apiClient = mock<ApiClient>()
  private val mapper = mock<LineMapper>()
  private val roomMapper = mock<LineRoomMapper>()
  private val cache = mock<LineCache>()
  private val lineDao = mock<LineDao>()

  private val repository = LineRepository(apiClient, mapper, roomMapper, cache, lineDao)

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
    val line = Line(1, "1", "syn", "name", "company", 1, "style")
    val lineEntity = mock<LineEntity>()
    val lines = listOf(line)
    val lineEntities = listOf(lineEntity)
    whenever(cache.getAll()).thenFailure(mock())
    whenever(apiClient.getLines()).thenSuccess(lineEntities)
    whenever(mapper.toDomain(lineEntity)).thenReturn(line)
    whenever(roomMapper.toEntity(any())).thenReturn(mock<org.galio.bussantiago.data.local.room.entity.LineEntity>())

    val result = repository.getLines()

    verify(cache).save(lines)
    verify(lineDao).clearAndInsertAll(any())
    assertEquals(lines, result.getOrNull())
  }

  @Test
  fun `when cache data is not valid and service fails and no fallback should return the service exception`() {
    val exception = ServiceException()
    whenever(cache.getAll()).thenFailure(mock())
    whenever(apiClient.getLines()).thenFailure(exception)
    whenever(lineDao.getAll()).thenReturn(emptyList())

    val result = repository.getLines()

    assertEquals(exception, result.exceptionOrNull())
  }
  
  @Test
  fun `when cache data is not valid and service fails but fallback exists should return fallback`() {
    val exception = ServiceException()
    val line = Line(1, "1", "syn", "name", "company", 1, "style")
    val lineDbEntity = mock<org.galio.bussantiago.data.local.room.entity.LineEntity>()
    whenever(cache.getAll()).thenFailure(mock())
    whenever(apiClient.getLines()).thenFailure(exception)
    whenever(lineDao.getAll()).thenReturn(listOf(lineDbEntity))
    whenever(roomMapper.toDomain(lineDbEntity)).thenReturn(line)

    val result = repository.getLines()

    assertEquals(listOf(line), result.getOrNull())
  }
}
