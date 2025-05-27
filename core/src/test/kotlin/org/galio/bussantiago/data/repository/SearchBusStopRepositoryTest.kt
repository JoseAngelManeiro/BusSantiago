package org.galio.bussantiago.data.repository

import org.galio.bussantiago.core.model.BusStopSearch
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.cache.BusStopSearchCache
import org.galio.bussantiago.data.entity.BusStopRequest
import org.galio.bussantiago.data.entity.BusStopSearchEntity
import org.galio.bussantiago.data.exception.ServiceException
import org.galio.bussantiago.data.mapper.BusStopSearchMapper
import org.galio.bussantiago.util.mock
import org.galio.bussantiago.util.thenFailure
import org.galio.bussantiago.util.thenSuccess
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class SearchBusStopRepositoryTest {

  private val apiClient = mock<ApiClient>()
  private val mapper = mock<BusStopSearchMapper>()
  private val cache = mock<BusStopSearchCache>()

  private val repository = SearchBusStopRepository(apiClient, mapper, cache)

  @Test
  fun `when cache data is valid should return that data directly`() {
    val busStops = listOf(mock<BusStopSearch>())
    whenever(cache.getAll()).thenSuccess(busStops)

    val result = repository.searchAllBusStops()

    assertEquals(busStops, result.getOrNull())
  }

  @Test
  fun `when cache data is not valid should get data from service and save it in cache`() {
    val busStop = mock<BusStopSearch>()
    val busStopEntity = mock<BusStopSearchEntity>()
    val busStops = listOf(busStop)
    val busStopEntities = listOf(busStopEntity)
    whenever(cache.getAll()).thenFailure(mock())
    whenever(apiClient.searchBusStop(BusStopRequest(""))).thenSuccess(busStopEntities)
    whenever(mapper.toDomain(busStopEntity)).thenReturn(busStop)

    val result = repository.searchAllBusStops()

    verify(cache).save(busStops)
    assertEquals(busStops, result.getOrNull())
  }

  @Test
  fun `when cache data is not valid and service fails should return the exception from service`() {
    val exception = ServiceException()
    whenever(cache.getAll()).thenFailure(mock())
    whenever(apiClient.searchBusStop(BusStopRequest(""))).thenFailure(exception)

    val result = repository.searchAllBusStops()

    assertEquals(exception, result.exceptionOrNull())
  }
}
