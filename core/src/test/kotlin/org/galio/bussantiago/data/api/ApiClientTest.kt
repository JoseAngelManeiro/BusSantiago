package org.galio.bussantiago.data.api

import org.galio.bussantiago.data.entity.BusStopRemainingTimesEntity
import org.galio.bussantiago.data.entity.BusStopRequest
import org.galio.bussantiago.data.entity.BusStopSearchEntity
import org.galio.bussantiago.data.entity.LineDetailsEntity
import org.galio.bussantiago.data.entity.LineEntity
import org.galio.bussantiago.exception.NetworkConnectionException
import org.galio.bussantiago.exception.ServiceException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ApiClientTest : MockWebServerTest() {

  private lateinit var apiClient: ApiClient

  @Before
  override fun setUp() {
    super.setUp()
    apiClient = ApiClient(baseEndpoint)
  }

  @Test
  fun `sends get all lines request to the correct endpoint`() {
    enqueueMockResponse(200)

    apiClient.getLines()

    assertGetRequestSentTo("/lineas")
  }

  @Test
  fun `parses LineEntity list properly getting all the lines`() {
    enqueueMockResponse(200, "linesResponse.json")

    val lineEntities = apiClient.getLines().rightValue

    assertEquals(22, lineEntities.size)
    assertLineEntityContainsExpectedValues(lineEntities[0])
  }

  @Test
  fun `throws NetworkConnectionException if there is no connection`() {
    shutDownServer()

    val error = apiClient.getLines().leftValue

    assertTrue(error is NetworkConnectionException)
  }

  @Test
  fun `throws ServiceException if there is any error getting all the lines`() {
    enqueueMockResponse(400)

    val error = apiClient.getLines().leftValue

    assertTrue(error is ServiceException)
  }

  @Test
  fun `sends line details request to the correct endpoint`() {
    enqueueMockResponse(200)

    apiClient.getLineDetails(id = 15)

    assertGetRequestSentTo("/lineas/15?lang=es")
  }

  @Test
  fun `parses LineDetailsEntity properly getting the line details`() {
    enqueueMockResponse(200, "lineDetailsResponse.json")

    val lineDetailsEntity = apiClient.getLineDetails(id = 15).rightValue

    assertLineDetailsEntityContainsExpectedValues(lineDetailsEntity)
  }

  @Test
  fun `throws NetworkConnectionException if there is no connection getting the line details`() {
    shutDownServer()

    val error = apiClient.getLineDetails(id = 15).leftValue

    assertTrue(error is NetworkConnectionException)
  }

  @Test
  fun `throws ServiceException if there is any error getting the line details`() {
    enqueueMockResponse(400)

    val error = apiClient.getLineDetails(id = 15).leftValue

    assertTrue(error is ServiceException)
  }

  @Test
  fun `sends remaining times request to the correct endpoint`() {
    enqueueMockResponse(200)

    apiClient.getBusStopRemainingTimes("662")

    assertGetRequestSentTo("/lineas/0/parada/662")
  }

  @Test
  fun `parses BusStopRemainingTimesEntity properly getting the bus stop remaining times`() {
    enqueueMockResponse(200, "remainingTimesResponse.json")

    val busStopRemainingTimesEntity = apiClient.getBusStopRemainingTimes("662").rightValue

    assertBusStopRemainingTimesEntityExpectedValues(busStopRemainingTimesEntity)
  }

  @Test
  fun `throws NetworkConnectionException if there is no connection getting the remaining times`() {
    shutDownServer()

    val error = apiClient.getBusStopRemainingTimes("662").leftValue

    assertTrue(error is NetworkConnectionException)
  }

  @Test
  fun `throws ServiceException if there is any error getting the bus stop remaining times`() {
    enqueueMockResponse(400)

    val error = apiClient.getBusStopRemainingTimes("662").leftValue

    assertTrue(error is ServiceException)
  }

  @Test
  fun `sends Content Type Header searching a bus stop`() {
    apiClient.searchBusStop(BusStopRequest(nombre = "345"))

    assertRequestContainsHeader(
      "Content-Type",
      "application/json;charset=UTF-8"
    )
  }

  @Test
  fun `sends search a bus stop request to the correct endpoint`() {
    enqueueMockResponse(200)

    apiClient.searchBusStop(BusStopRequest(nombre = "345"))

    assertPostRequestSentTo("/paradas")
  }

  @Test
  fun `sends expected body searching a bus stop`() {
    enqueueMockResponse(200)

    apiClient.searchBusStop(BusStopRequest(nombre = "345"))

    assertRequestBodyEquals("searchBusStopRequest.json")
  }

  @Test
  fun `parses BusStopSearchEntity properly searching a bus stop`() {
    enqueueMockResponse(200, "busStopSearchResponse.json")

    val busStopSearchEntity = apiClient.searchBusStop(BusStopRequest(nombre = "345")).rightValue

    assertBusStopSearchEntityExpectedValues(busStopSearchEntity[0])
  }

  @Test
  fun `throws NetworkConnectionException if there is no connection searching a bus stop`() {
    shutDownServer()

    val error = apiClient.searchBusStop(BusStopRequest(nombre = "345")).leftValue

    assertTrue(error is NetworkConnectionException)
  }

  @Test
  fun `throws ServiceException if there is any error searching a bus stop`() {
    enqueueMockResponse(400)

    val error = apiClient.searchBusStop(BusStopRequest(nombre = "345")).leftValue

    assertTrue(error is ServiceException)
  }

  private fun assertLineEntityContainsExpectedValues(lineEntity: LineEntity?) {
    assertTrue(lineEntity != null)
    assertEquals(lineEntity?.id, 1)
    assertEquals(lineEntity?.codigo, "1")
    assertEquals(lineEntity?.sinoptico, "L1")
    assertEquals(lineEntity?.nombre, "Cemiterio de Boisaca / hospital Clínico")
    assertEquals(lineEntity?.empresa, "TRALUSA")
    assertEquals(lineEntity?.incidencias, 1)
    assertEquals(lineEntity?.estilo, "#f32621")
  }

  private fun assertLineDetailsEntityContainsExpectedValues(lineDetailsEntity: LineDetailsEntity?) {
    assertTrue(lineDetailsEntity != null)
    assertEquals(lineDetailsEntity?.id, 15)
    assertEquals(lineDetailsEntity?.codigo, "15")
    assertEquals(lineDetailsEntity?.sinoptico, "L15")
    assertEquals(lineDetailsEntity?.nombre, "Campus Norte / Campus Sur")
    assertEquals(lineDetailsEntity?.informacion, "Información")
    assertEquals(lineDetailsEntity?.estilo, "#7a4b2a")

    assertEquals(
      lineDetailsEntity?.trayectos?.get(0)?.nombre,
      "Vite de Arriba - Campus Sur"
    )
    assertEquals(lineDetailsEntity?.trayectos?.get(0)?.sentido, "IDA")
    assertEquals(lineDetailsEntity?.trayectos?.get(0)?.paradas?.get(0)?.id, 669)
    assertEquals(lineDetailsEntity?.trayectos?.get(0)?.paradas?.get(0)?.codigo, "669")
    assertEquals(
      lineDetailsEntity?.trayectos?.get(0)?.paradas?.get(0)?.nombre,
      "Vite de Arriba nº 35"
    )
    assertEquals(
      lineDetailsEntity?.trayectos?.get(0)?.paradas?.get(0)?.zona,
      "Vite"
    )
    assertEquals(
      lineDetailsEntity?.trayectos?.get(0)?.paradas?.get(0)?.extraordinaria,
      true
    )
    assertEquals(
      lineDetailsEntity?.trayectos?.get(0)?.paradas?.get(0)?.coordenadas?.latitud,
      42.8963479268562
    )
    assertEquals(
      lineDetailsEntity?.trayectos?.get(0)?.paradas?.get(0)?.coordenadas?.longitud,
      -8.54296579957008
    )

    assertEquals(lineDetailsEntity?.incidencias?.get(0)?.id, 298)
    assertEquals(
      lineDetailsEntity?.incidencias?.get(0)?.titulo,
      "Peonalización Ensanche"
    )
    assertEquals(
      lineDetailsEntity?.incidencias?.get(0)?.descripcion,
      "Cualquier descripción"
    )
    assertEquals(
      lineDetailsEntity?.incidencias?.get(0)?.inicio,
      "2020-07-02 02:00"
    )
    assertEquals(lineDetailsEntity?.incidencias?.get(0)?.fin, null)
  }

  private fun assertBusStopRemainingTimesEntityExpectedValues(
    busStopRemainingTimesEntity: BusStopRemainingTimesEntity?
  ) {
    assertTrue(busStopRemainingTimesEntity != null)
    assertEquals(busStopRemainingTimesEntity?.id, 662)
    assertEquals(busStopRemainingTimesEntity?.codigo, "662")
    assertEquals(busStopRemainingTimesEntity?.nombre, "Virxe da Cerca nº 10")
    assertEquals(busStopRemainingTimesEntity?.zona, "Casco antiguo")
    assertEquals(busStopRemainingTimesEntity?.coordenadas?.latitud, 42.8804523843327)
    assertEquals(busStopRemainingTimesEntity?.coordenadas?.longitud, -8.54050487279892)
    assertEquals(busStopRemainingTimesEntity?.lineas?.get(0)?.id, 11)
    assertEquals(busStopRemainingTimesEntity?.lineas?.get(0)?.sinoptico, "C11")
    assertEquals(
      busStopRemainingTimesEntity?.lineas?.get(0)?.nombre,
      "Circular de Fontiñas"
    )
    assertEquals(busStopRemainingTimesEntity?.lineas?.get(0)?.estilo, "#aec741")
    assertEquals(
      busStopRemainingTimesEntity?.lineas?.get(0)?.proximoPaso,
      "2020-11-15 12:59"
    )
    assertEquals(busStopRemainingTimesEntity?.lineas?.get(0)?.minutosProximoPaso, 5)
  }

  private fun assertBusStopSearchEntityExpectedValues(busStopSearchEntity: BusStopSearchEntity?) {
    assertTrue(busStopSearchEntity != null)
    assertEquals(busStopSearchEntity?.id, 345)
    assertEquals(busStopSearchEntity?.codigo, "345")
    assertEquals(busStopSearchEntity?.nombre, "Os Tilos (rúa Carballo - Hotel)")
    assertEquals(busStopSearchEntity?.zona, "Os Tilos")
    assertEquals(busStopSearchEntity?.coordenadas?.latitud, 42.8462243073367)
    assertEquals(busStopSearchEntity?.coordenadas?.longitud, -8.53974312543869)
    assertEquals(busStopSearchEntity?.lineas?.get(0)?.sinoptico, "L6")
    assertEquals(busStopSearchEntity?.lineas?.get(0)?.estilo, "#d73983")
    assertEquals(busStopSearchEntity?.lineas?.get(1)?.sinoptico, "L12")
    assertEquals(busStopSearchEntity?.lineas?.get(1)?.estilo, "#842e14")
  }
}
