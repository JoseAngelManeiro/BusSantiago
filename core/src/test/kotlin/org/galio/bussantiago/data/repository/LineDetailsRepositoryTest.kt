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

class LineDetailsRepositoryTest {

  private val apiClient = mock<ApiClient>()
  private val mapper = mock<LineDetailsMapper>()
  private val cache = mock<LineDetailsCache>()

  private val repository = LineDetailsRepository(apiClient, mapper, cache)

  @Test
  fun `when cache data is valid should return that data directly`() {
    val id = 123
    val lineDetails = mock<LineDetails>()
    whenever(cache.get(id)).thenSuccess(lineDetails)

    val result = repository.getLineDetails(id)

    assertEquals(lineDetails, result.getOrNull())
  }

  @Test
  fun `when cache data is not valid should get data from service and save it in cache`() {
    val id = 123
    val lineDetails = mock<LineDetails>()
    val lineDetailsEntity = mock<LineDetailsEntity>()
    whenever(cache.get(id)).thenFailure(mock())
    whenever(apiClient.getLineDetails(id)).thenSuccess(lineDetailsEntity)
    whenever(mapper.toDomain(lineDetailsEntity)).thenReturn(lineDetails)

    val result = repository.getLineDetails(id)

    verify(cache).save(id, lineDetails)
    assertEquals(lineDetails, result.getOrNull())
  }

  @Test
  fun `when cache data is not valid and service fails should return the service exception`() {
    val id = 123
    val exception = ServiceException()
    whenever(cache.get(id)).thenFailure(NoSuchElementException())
    whenever(apiClient.getLineDetails(id)).thenFailure(exception)

    val result = repository.getLineDetails(id)

    assertEquals(exception, result.exceptionOrNull())
  }
}
