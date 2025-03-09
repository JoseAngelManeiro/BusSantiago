package org.galio.bussantiago.data.repository

import org.galio.bussantiago.Either
import org.galio.bussantiago.core.model.BusStopSearch
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.cache.BusStopSearchCache
import org.galio.bussantiago.data.entity.BusStopRequest
import org.galio.bussantiago.data.entity.BusStopSearchEntity
import org.galio.bussantiago.data.mapper.BusStopSearchMapper
import org.galio.bussantiago.util.mock
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.BDDMockito.given

class SearchBusStopRepositoryTest {

  private val apiClient = mock<ApiClient>()
  private val mapper = mock<BusStopSearchMapper>()
  private val cache = BusStopSearchCache()

  private val repository = SearchBusStopRepository(apiClient, mapper, cache)

  @Test
  fun `when cache data is valid should return that data directly`() {
    val busStops = listOf(mock<BusStopSearch>())
    cache.save(busStops)

    val result = repository.searchAllBusStops()

    assertEquals(busStops, result.rightValue)
  }

  @Test
  fun `when cache data is not valid should get data from service and save it in cache`() {
    val busStop = mock<BusStopSearch>()
    val busStopEntity = mock<BusStopSearchEntity>()
    val busStops = listOf(busStop)
    val busStopEntities = listOf(busStopEntity)
    given(apiClient.searchBusStop(BusStopRequest("")))
      .willReturn(Either.right(busStopEntities))
    given(mapper.toDomain(busStopEntity))
      .willReturn(busStop)

    val result = repository.searchAllBusStops()

    assertEquals(busStops, cache.getAll())
    assertEquals(busStops, result.rightValue)
  }

  @Test
  fun `when cache data is not valid and service fails should return the exception`() {
    val exception = mock<Exception>()
    given(apiClient.searchBusStop(BusStopRequest(""))).willReturn(Either.left(exception))

    val result = repository.searchAllBusStops()

    assertEquals(exception, result.leftValue)
  }
}
