package org.galio.bussantiago.data.api

import org.galio.bussantiago.data.entity.BusStopEntity
import org.galio.bussantiago.data.entity.BusStopRemainingTimesEntity
import org.galio.bussantiago.data.entity.BusStopRequest
import org.galio.bussantiago.data.entity.BusStopSearchEntity
import org.galio.bussantiago.data.entity.CoordinatesEntity
import org.galio.bussantiago.data.entity.IncidenceEntity
import org.galio.bussantiago.data.entity.LineDetailsEntity
import org.galio.bussantiago.data.entity.LineEntity
import org.galio.bussantiago.data.entity.LineRemainingTimeEntity
import org.galio.bussantiago.data.entity.LineSearchEntity
import org.galio.bussantiago.data.entity.RouteEntity
import org.galio.bussantiago.data.exception.NetworkConnectionException
import org.galio.bussantiago.data.exception.ServiceException
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
  fun `when there is no connection should throw NetworkConnectionException`() {
    shutDownServer()

    val response = apiClient.getLines()

    assertTrue(response.leftValue is NetworkConnectionException)
  }

  @Test
  fun `when there is a non valid server response should throw ServiceException`() {
    enqueueMockResponse(400)

    val response = apiClient.getLines()

    assertTrue(response.leftValue is ServiceException)
  }

  @Test
  fun `when there is valid response but with empty body should throw ServiceException`() {
    enqueueMockResponse(200, fileName = null)

    val response = apiClient.getLines()

    assertTrue(response.leftValue is ServiceException)
  }

  @Test
  fun `when invoke getLines should send the request and get the value expected`() {
    enqueueMockResponse(200, "linesResponse.json")

    val response = apiClient.getLines()

    assertGetRequestSentTo("/lineas")
    assertEquals(lineEntitiesExpected, response.rightValue)
  }

  @Test
  fun `when invoke getLineDetails should send the request and get the value expected`() {
    enqueueMockResponse(200, "lineDetailsResponse.json")

    val response = apiClient.getLineDetails(id = 15)

    assertGetRequestSentTo("/lineas/15?lang=es")
    assertEquals(lineDetailsEntityExpected, response.rightValue)
  }

  @Test
  fun `when invoke getBusStopRemainingTimes should send the request and get the value expected`() {
    enqueueMockResponse(200, "remainingTimesResponse.json")

    val response = apiClient.getBusStopRemainingTimes("662")

    assertGetRequestSentTo("/lineas/0/parada/662")
    assertEquals(busStopRemainingTimesEntityExpected, response.rightValue)
  }

  @Test
  fun `when invoke searchBusStop should send the request and get the value expected`() {
    enqueueMockResponse(200, "busStopSearchResponse.json")

    val response = apiClient.searchBusStop(BusStopRequest(nombre = "345"))

    assertPostRequestSentTo("/paradas")
    assertEquals(busStopSearchEntitiesExpected, response.rightValue)
  }

  @Test
  fun `when invoke searchBusStop should send the header expected`() {
    enqueueMockResponse(200, "busStopSearchResponse.json")

    apiClient.searchBusStop(BusStopRequest(nombre = "345"))

    assertRequestContainsHeader("Content-Type", "application/json;charset=UTF-8")
  }

  @Test
  fun `when invoke searchBusStop should send the body expected`() {
    enqueueMockResponse(200, "busStopSearchResponse.json")

    apiClient.searchBusStop(BusStopRequest(nombre = "345"))

    assertRequestBodyEquals("searchBusStopRequest.json")
  }

  private val lineEntitiesExpected = listOf(
    LineEntity(
      1,
      "1",
      "L1",
      "Cemiterio de Boisaca / hospital Clínico",
      "TRALUSA",
      1,
      "#f32621"
    ),
    LineEntity(
      4,
      "4",
      "L4",
      "Romaño / As Cancelas",
      "TRALUSA",
      1,
      "#ffcc33"
    )
  )

  private val lineDetailsEntityExpected = LineDetailsEntity(
    15,
    "15",
    "L15",
    "Campus Norte / Campus Sur",
    "Información",
    "#7a4b2a",
    listOf(
      RouteEntity(
        "Vite de Arriba - Campus Sur",
        "IDA",
        listOf(
          BusStopEntity(
            669,
            "669",
            "Vite de Arriba nº 35",
            "Vite",
            true,
            CoordinatesEntity(42.8963479268562, -8.54296579957008)
          )
        )
      )
    ),
    listOf(
      IncidenceEntity(
        298,
        "Peonalización Ensanche",
        "Cualquier descripción",
        "2020-07-02 02:00",
        null
      )
    )
  )

  private val busStopRemainingTimesEntityExpected = BusStopRemainingTimesEntity(
    662,
    "662",
    "Virxe da Cerca nº 10",
    "Casco antiguo",
    CoordinatesEntity(42.8804523843327, -8.54050487279892),
    listOf(
      LineRemainingTimeEntity(
        11,
        "C11",
        "Circular de Fontiñas",
        "#aec741",
        "2020-11-15 12:59",
        5
      )
    )
  )

  private val busStopSearchEntitiesExpected = listOf(
    BusStopSearchEntity(
      345,
      "345",
      "Os Tilos (rúa Carballo - Hotel)",
      "Os Tilos",
      CoordinatesEntity(42.8462243073367, -8.53974312543869),
      listOf(
        LineSearchEntity("L6", "#d73983"),
        LineSearchEntity("L12", "#842e14")
      )
    )
  )
}
