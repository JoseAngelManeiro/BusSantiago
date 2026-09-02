package org.galio.bussantiago.data.repository

import org.galio.bussantiago.core.model.BusStopSearch
import org.galio.bussantiago.core.model.Coordinates
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.entity.BusStopRequest
import org.galio.bussantiago.data.entity.BusStopSearchEntity
import org.galio.bussantiago.data.exception.ServiceException
import org.galio.bussantiago.data.local.room.BusStopDao
import org.galio.bussantiago.data.local.room.BusStopEntity
import org.galio.bussantiago.data.local.room.toEntity
import org.galio.bussantiago.data.mapper.BusStopSearchMapper
import org.galio.bussantiago.util.mock
import org.galio.bussantiago.util.thenFailure
import org.galio.bussantiago.util.thenSuccess
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class SearchBusStopRepositoryTest {

  private val apiClient = mock<ApiClient>()
  private val mapper = mock<BusStopSearchMapper>()
  private val busStopDao = mock<BusStopDao>()
  private val sharedPreferences = mock<android.content.SharedPreferences>()
  private val sharedPrefsEditor = mock<android.content.SharedPreferences.Editor>()

  private val repository = SearchBusStopRepository(apiClient, mapper, busStopDao, sharedPreferences)

  init {
    whenever(sharedPreferences.edit()).thenReturn(sharedPrefsEditor)
    whenever(sharedPrefsEditor.putLong(any(), any())).thenReturn(sharedPrefsEditor)
    // Make sure valid cache hits always work by setting last update to now
    whenever(sharedPreferences.getLong(any(), any())).thenReturn(System.currentTimeMillis())
  }

  @Test
  fun `when cache data is valid should return that data directly`() {
    val busStopEntity = BusStopEntity(1, "1", "name", "zone", 42.0, -8.0, "[]")
    val busStopsEntities = listOf(busStopEntity)
    val expectedDomain = busStopEntity.toDomain()
    whenever(busStopDao.getAll()).thenReturn(busStopsEntities)
    whenever(apiClient.searchBusStop(any())).thenFailure(ServiceException())

    val result = repository.searchAllBusStops()

    assertEquals(listOf(expectedDomain), result.getOrNull())
  }

  @Test
  fun `when cache data is not valid should get data from service and save it in cache`() {
    val busStopEntity = mock<BusStopSearchEntity>()
    val busStopSearch = BusStopSearch(1, "1", "name", "zone", Coordinates(42.0, -8.0), emptyList())
    val busStopEntities = listOf(busStopEntity)
    
    // First call to getAll() returns empty
    whenever(busStopDao.getAll()).thenReturn(emptyList(), listOf(busStopSearch.toEntity()))
    
    whenever(apiClient.searchBusStop(BusStopRequest(""))).thenSuccess(busStopEntities)
    whenever(mapper.toDomain(busStopEntity)).thenReturn(busStopSearch)

    val result = repository.searchAllBusStops()

    verify(busStopDao).clearAndInsertAll(any())
    assertEquals(listOf(busStopSearch), result.getOrNull())
  }

  @Test
  fun `when cache data is not valid and service fails should return the exception from service`() {
    val exception = ServiceException()
    whenever(busStopDao.getAll()).thenReturn(emptyList())
    whenever(apiClient.searchBusStop(BusStopRequest(""))).thenFailure(exception)

    val result = repository.searchAllBusStops()

    assertEquals(exception, result.exceptionOrNull())
  }
}
