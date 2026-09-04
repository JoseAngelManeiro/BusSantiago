package org.galio.bussantiago.data.repository

import org.galio.bussantiago.core.model.LineDetails
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.cache.LineDetailsCache
import org.galio.bussantiago.data.entity.LineDetailsEntity
import org.galio.bussantiago.data.exception.ServiceException
import org.galio.bussantiago.data.mapper.LineDetailsMapper
import org.galio.bussantiago.util.mock
import org.galio.bussantiago.util.thenFailure
import org.galio.bussantiago.util.thenSuccess
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

import org.galio.bussantiago.data.local.room.LineDetailsDao
import org.galio.bussantiago.data.mapper.LineDetailsRoomMapper
import org.mockito.kotlin.any
import org.galio.bussantiago.data.local.room.LineDetailsEntity as RoomLineDetailsEntity

class LineDetailsRepositoryTest {

  private val apiClient = mock<ApiClient>()
  private val mapper = mock<LineDetailsMapper>()
  private val roomMapper = mock<LineDetailsRoomMapper>()
  private val cache = mock<LineDetailsCache>()
  private val lineDetailsDao = mock<LineDetailsDao>()

  private val repository = LineDetailsRepository(apiClient, mapper, roomMapper, cache, lineDetailsDao)

  @Test
  fun `when cache data is valid should return that data directly`() {
    val id = 123
    val lineDetails = mock<LineDetails>()
    whenever(cache.get(id)).thenSuccess(lineDetails)

    val result = repository.getLineDetails(id)

    assertEquals(lineDetails, result.getOrNull())
  }

  @Test
  fun `when cache data is not valid should get data from service and save it in cache and db`() {
    val id = 123
    val lineDetails = mock<LineDetails>()
    val lineDetailsEntity = mock<LineDetailsEntity>()
    whenever(cache.get(id)).thenFailure(mock())
    whenever(apiClient.getLineDetails(id)).thenSuccess(lineDetailsEntity)
    whenever(mapper.toDomain(lineDetailsEntity)).thenReturn(lineDetails)
    whenever(roomMapper.toEntity(any())).thenReturn(mock<RoomLineDetailsEntity>())

    val result = repository.getLineDetails(id)

    verify(cache).save(id, lineDetails)
    verify(lineDetailsDao).insert(any())
    assertEquals(lineDetails, result.getOrNull())
  }

  @Test
  fun `when cache data is not valid and service fails and no fallback should return the service exception`() {
    val id = 123
    val exception = ServiceException()
    whenever(cache.get(id)).thenFailure(NoSuchElementException())
    whenever(apiClient.getLineDetails(id)).thenFailure(exception)
    whenever(lineDetailsDao.get(id)).thenReturn(null)

    val result = repository.getLineDetails(id)

    assertEquals(exception, result.exceptionOrNull())
  }

  @Test
  fun `when cache data is not valid and service fails but fallback exists should return fallback`() {
    val id = 123
    val exception = ServiceException()
    val lineDetails = mock<LineDetails>()
    val roomEntity = mock<RoomLineDetailsEntity>()
    whenever(cache.get(id)).thenFailure(NoSuchElementException())
    whenever(apiClient.getLineDetails(id)).thenFailure(exception)
    whenever(lineDetailsDao.get(id)).thenReturn(roomEntity)
    whenever(roomMapper.toDomain(roomEntity)).thenReturn(lineDetails)

    val result = repository.getLineDetails(id)

    assertEquals(lineDetails, result.getOrNull())
  }
}
