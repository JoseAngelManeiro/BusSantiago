package org.galio.bussantiago.data.repository

import org.galio.bussantiago.core.Either
import org.galio.bussantiago.core.model.BusStopRemainingTimes
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.entity.BusStopRemainingTimesEntity
import org.galio.bussantiago.data.exception.ServiceException
import org.galio.bussantiago.data.mapper.BusStopRemainingTimesMapper
import org.galio.bussantiago.util.mock
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.whenever

class BusStopRemainingTimesRepositoryTest {

  private val apiClient = mock<ApiClient>()
  private val mapper = mock<BusStopRemainingTimesMapper>()

  private val repository = BusStopRemainingTimesRepository(apiClient, mapper)

  @Test
  fun `when api client returns a successful response should return the date mapped`() {
    val busCode = "123"
    val busStopRemainingTimesEntity = mock<BusStopRemainingTimesEntity>()
    val busStopRemainingTimes = mock<BusStopRemainingTimes>()
    whenever(apiClient.getBusStopRemainingTimes(busCode))
      .thenReturn(Either.success(busStopRemainingTimesEntity))
    whenever(mapper.toDomain(busStopRemainingTimesEntity))
      .thenReturn(busStopRemainingTimes)

    val result = repository.getBusStopRemainingTimes(busCode)

    assertEquals(busStopRemainingTimes, result.successValue)
  }

  @Test
  fun `when api client fails should return the exception received`() {
    val busCode = "123"
    val exception = ServiceException()
    whenever(apiClient.getBusStopRemainingTimes(busCode))
      .thenReturn(Either.error(exception))

    val result = repository.getBusStopRemainingTimes(busCode)

    assertEquals(exception, result.errorValue)
  }
}
