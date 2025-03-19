package org.galio.bussantiago.data.cache

import org.galio.bussantiago.core.model.BusStopSearch
import org.galio.bussantiago.util.mock
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BusStopSearchCacheTest {

  private lateinit var busStopSearchCache: BusStopSearchCache

  @Before
  fun setUp() {
    busStopSearchCache = BusStopSearchCache()
  }

  @Test
  fun `when cache has no data should return an empty list`() {
    assertEquals(emptyList<BusStopSearch>(), busStopSearchCache.getAll())
  }

  @Test
  fun `when cache has data should return the data saved`() {
    val busStops = listOf(mock<BusStopSearch>(), mock<BusStopSearch>())

    busStopSearchCache.save(busStops)

    assertEquals(busStops, busStopSearchCache.getAll())
  }

  @Test
  fun `when cache saves new data should remove all previous information`() {
    val oldBusStops = listOf<BusStopSearch>(mock(), mock())
    val newBusStops = listOf<BusStopSearch>(mock(), mock())

    busStopSearchCache.save(oldBusStops)
    assertEquals(oldBusStops, busStopSearchCache.getAll())

    busStopSearchCache.save(newBusStops)
    assertEquals(newBusStops, busStopSearchCache.getAll())
  }
}
