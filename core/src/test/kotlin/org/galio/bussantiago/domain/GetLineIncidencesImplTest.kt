package org.galio.bussantiago.domain

import org.galio.bussantiago.core.model.Incidence
import org.galio.bussantiago.core.model.LineDetails
import org.galio.bussantiago.data.exception.ServiceException
import org.galio.bussantiago.data.repository.LineDetailsRepository
import org.galio.bussantiago.util.mock
import org.galio.bussantiago.util.thenFailure
import org.galio.bussantiago.util.thenSuccess
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever

class GetLineIncidencesImplTest {

  private val lineDetailsRepository = mock<LineDetailsRepository>()
  private val getLineIncidences = GetLineIncidencesImpl(lineDetailsRepository)

  @Test
  fun `invokes repository and returns the incidences`() {
    val lineId = 123
    val incidences = mock<List<Incidence>>()
    val lineDetailsStub = createLineDetails(incidences)
    whenever(lineDetailsRepository.getLineDetails(lineId)).thenSuccess(lineDetailsStub)

    val result = getLineIncidences(lineId)

    verify(lineDetailsRepository).getLineDetails(lineId)
    assertTrue(result.isSuccess)
    assertEquals(incidences, result.getOrNull())
  }

  @Test
  fun `if the repository fails, returns the exception received`() {
    val lineId = 123
    val exception = ServiceException()
    whenever(lineDetailsRepository.getLineDetails(lineId)).thenFailure(exception)

    val result = getLineIncidences(lineId)

    verify(lineDetailsRepository).getLineDetails(lineId)
    assertTrue(result.isFailure)
    assertEquals(exception, result.exceptionOrNull())
  }

  private fun createLineDetails(incidences: List<Incidence>): LineDetails {
    return LineDetails(
      id = 1,
      code = "Any code",
      synoptic = "Any Synoptic",
      name = "Any name",
      information = "Any information",
      style = "Any color",
      routes = emptyList(),
      incidences = incidences
    )
  }
}
