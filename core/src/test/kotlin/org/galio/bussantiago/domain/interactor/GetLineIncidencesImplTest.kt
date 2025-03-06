package org.galio.bussantiago.domain.interactor

import org.galio.bussantiago.Either
import org.galio.bussantiago.domain.model.Incidence
import org.galio.bussantiago.domain.model.LineDetails
import org.galio.bussantiago.domain.repository.LineDetailsRepository
import org.galio.bussantiago.exception.ServiceException
import org.galio.bussantiago.util.mock
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.mockito.Mockito.verify

class GetLineIncidencesImplTest {

  private val lineDetailsRepository = Mockito.mock(LineDetailsRepository::class.java)
  private val getLineIncidences = GetLineIncidencesImpl(lineDetailsRepository)

  @Test
  fun `invokes repository and returns the incidences`() {
    val lineId = 123
    val incidences = mock<List<Incidence>>()
    val lineDetailsStub = createLineDetails(incidences)
    given(lineDetailsRepository.getLineDetails(lineId)).willReturn(Either.Right(lineDetailsStub))

    val result = getLineIncidences(lineId)

    verify(lineDetailsRepository).getLineDetails(lineId)
    assertTrue(result.isRight)
    assertEquals(incidences, result.rightValue)
  }

  @Test
  fun `if the repository fails, returns the exception received`() {
    val lineId = 123
    val exception = ServiceException()
    given(lineDetailsRepository.getLineDetails(lineId)).willReturn(Either.Left(exception))

    val result = getLineIncidences(lineId)

    verify(lineDetailsRepository).getLineDetails(lineId)
    assertTrue(result.isLeft)
    assertEquals(exception, result.leftValue)
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
