package org.galio.bussantiago.domain

import org.galio.bussantiago.core.model.LineDetails
import org.galio.bussantiago.data.exception.ServiceException
import org.galio.bussantiago.data.repository.LineDetailsRepository
import org.galio.bussantiago.util.mock
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify

class GetLineInformationImplTest {

  private val lineDetailsRepository = mock<LineDetailsRepository>()
  private val getLineInformation = GetLineInformationImpl(lineDetailsRepository)

  @Test
  fun `invokes repository and returns the information field`() {
    val lineId = 123
    val information = "Any Information"
    val lineDetailsStub = createLineDetails(information)
    given(lineDetailsRepository.getLineDetails(lineId))
      .willReturn(Result.success(lineDetailsStub))

    val result = getLineInformation(lineId)

    verify(lineDetailsRepository).getLineDetails(lineId)
    assertTrue(result.isSuccess)
    assertEquals("Any Information", result.getOrNull())
  }

  @Test
  fun `if the repository fails, returns the exception received`() {
    val lineId = 123
    val exception = ServiceException()
    given(lineDetailsRepository.getLineDetails(lineId))
      .willReturn(Result.failure(exception))

    val result = getLineInformation(lineId)

    verify(lineDetailsRepository).getLineDetails(lineId)
    assertTrue(result.isFailure)
    assertEquals(exception, result.exceptionOrNull())
  }

  private fun createLineDetails(information: String): LineDetails {
    return LineDetails(
      id = 1,
      code = "Any code",
      synoptic = "Any Synoptic",
      name = "Any name",
      information = information,
      style = "Any color",
      routes = emptyList(),
      incidences = emptyList()
    )
  }
}
