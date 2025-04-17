package org.galio.bussantiago.features.search

import org.galio.bussantiago.core.model.BusStopSearch
import org.galio.bussantiago.core.model.Coordinates
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SearchUtilsTest {

  private val searchUtils = SearchUtils()

  private val busStopSearch =
    BusStopSearch(
      id = 0,
      code = "",
      name = "",
      zone = null,
      coordinates = Coordinates(0.0, 0.0),
      lines = emptyList()
    )

  private val stops = listOf(
    busStopSearch.copy(code = "001", name = "Óscar Romero"),
    busStopSearch.copy(code = "002", name = "25th Main Street"),
    busStopSearch.copy(code = "003", name = "Central Station"),
    busStopSearch.copy(code = "004", name = "Parque del Este"),
    busStopSearch.copy(code = "005", name = "Estación Central"),
    busStopSearch.copy(code = "006", name = "Avenida Bolívar"),
  )

  @Test
  fun `matches exact name ignoring accents`() {
    val result = searchUtils.filterBusStops(stops, "oscar romero")

    assertEquals(1, result.size)
    assertEquals("001", result.first().code)
    assertEquals("Óscar Romero", result.first().name)
  }

  @Test
  fun `matches partial name from middle`() {
    val result = searchUtils.filterBusStops(stops, "main")

    assertEquals(1, result.size)
    assertEquals("002", result.first().code)
    assertEquals("25th Main Street", result.first().name)
  }

  @Test
  fun `matches using code and name together`() {
    val result = searchUtils.filterBusStops(stops, "002 main")

    assertEquals(1, result.size)
    assertEquals("002", result.first().code)
    assertEquals("25th Main Street", result.first().name)
  }

  @Test
  fun `matches multiple tokens in any order`() {
    val result = searchUtils.filterBusStops(stops, "romero oscar")

    assertEquals(1, result.size)
    assertEquals("001", result.first().code)
    assertEquals("Óscar Romero", result.first().name)
  }

  @Test
  fun `matches accent-insensitive spanish words`() {
    val result = searchUtils.filterBusStops(stops, "estacion central")

    assertEquals(1, result.size)
    assertEquals("005", result.first().code)
    assertEquals("Estación Central", result.first().name)
  }

  @Test
  fun `returns empty list if query doesn't match anything`() {
    val result = searchUtils.filterBusStops(stops, "random place")

    assertTrue(result.isEmpty())
  }

  @Test
  fun `returns empty list for blank input`() {
    val result = searchUtils.filterBusStops(stops, "")

    assertTrue(result.isEmpty())
  }

  @Test
  fun `matches name with special characters removed`() {
    val result = searchUtils.filterBusStops(stops, "bolivar")

    assertEquals(1, result.size)
    assertEquals("006", result.first().code)
    assertEquals("Avenida Bolívar", result.first().name)
  }

  @Test
  fun `matches if all tokens exist even if spread out`() {
    val result = searchUtils.filterBusStops(stops, "avenida 006")

    assertEquals(1, result.size)
    assertEquals("006", result.first().code)
    assertEquals("Avenida Bolívar", result.first().name)
  }
}
