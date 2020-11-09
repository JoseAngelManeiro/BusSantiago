package org.galio.bussantiago.data.api

import org.galio.bussantiago.data.entity.LineEntity
import org.galio.bussantiago.exception.NetworkConnectionException
import org.galio.bussantiago.exception.ServiceException
import org.galio.bussantiago.util.mock
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given

class ApiClientTest : MockWebServerTest() {

  private val networkHandler = mock<NetworkHandler>()

  private lateinit var apiClient: ApiClient

  @Before
  override fun setUp() {
    super.setUp()
    val mockWebServerEndpoint = baseEndpoint
    apiClient = ApiClient(networkHandler = networkHandler, baseEndpoint = mockWebServerEndpoint)
  }

  @Test
  fun `parses lines properly getting all the lines`() {
    enqueueMockResponse(200, "lineasResponse.json")
    given(networkHandler.isConnected()).willReturn(true)

    val lines = apiClient.getLines().rightValue

    assertEquals(22, lines.size)
    assertLineEntityContainsExpectedValues(lines[0])
  }

  @Test
  fun `throws NetworkConnectionException if there is no connection`() {
    given(networkHandler.isConnected()).willReturn(false)

    val error = apiClient.getLines().leftValue

    assertTrue(error is NetworkConnectionException)
  }

  @Test
  fun `throws ServiceException if there is any error getting all the lines`() {
    enqueueMockResponse(400)
    given(networkHandler.isConnected()).willReturn(true)

    val error = apiClient.getLines().leftValue

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
}
