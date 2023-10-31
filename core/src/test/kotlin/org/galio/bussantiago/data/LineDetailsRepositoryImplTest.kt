package org.galio.bussantiago.data

import org.galio.bussantiago.Either
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.cache.LineDetailsCache
import org.galio.bussantiago.data.entity.LineDetailsEntity
import org.galio.bussantiago.data.mapper.LineDetailsMapper
import org.galio.bussantiago.domain.model.LineDetails
import org.galio.bussantiago.util.mock
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.BDDMockito.given

class LineDetailsRepositoryImplTest {

  private val apiClient = mock<ApiClient>()
  private val mapper = mock<LineDetailsMapper>()
  private val cache = LineDetailsCache()

  private val repository = LineDetailsRepositoryImpl(apiClient, mapper, cache)

  @Test
  fun `when cache data is valid should return that data directly`() {
    val id = 123
    val lineDetails = mock<LineDetails>()
    cache.save(id, lineDetails)

    val result = repository.getLineDetails(id)

    assertEquals(lineDetails, result.rightValue)
  }

  @Test
  fun `when cache data is not valid should get data from service and save it in cache`() {
    val id = 123
    val lineDetails = mock<LineDetails>()
    val lineDetailsEntity = mock<LineDetailsEntity>()
    given(apiClient.getLineDetails(id)).willReturn(Either.right(lineDetailsEntity))
    given(mapper.toDomain(lineDetailsEntity)).willReturn(lineDetails)

    val result = repository.getLineDetails(id)

    assertEquals(lineDetails, cache.get(id))
    assertEquals(lineDetails, result.rightValue)
  }

  @Test
  fun `when cache data is not valid and service fails should return the exception`() {
    val id = 123
    val exception = mock<Exception>()
    given(apiClient.getLineDetails(id)).willReturn(Either.left(exception))

    val result = repository.getLineDetails(id)

    assertEquals(exception, result.leftValue)
  }
}
