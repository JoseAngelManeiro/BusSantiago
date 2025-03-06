package org.galio.bussantiago.domain.interactor

import org.galio.bussantiago.Either
import org.galio.bussantiago.domain.model.LineDetails
import org.galio.bussantiago.domain.repository.LineDetailsRepository
import org.galio.bussantiago.exception.ServiceException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.Mockito

class GetLineInformationImplTest {

  private val lineDetailsRepository = Mockito.mock(LineDetailsRepository::class.java)
  private val getLineInformation = GetLineInformationImpl(lineDetailsRepository)

  @Test
  fun `invokes repository and returns the information field`() {
    val lineId = 123
    val information = "Any Information"
    val lineDetailsStub = createLineDetails(information)
    given(lineDetailsRepository.getLineDetails(lineId)).willReturn(Either.Right(lineDetailsStub))

    val result = getLineInformation(lineId)

    verify(lineDetailsRepository).getLineDetails(lineId)
    assertTrue(result.isRight)
    assertEquals("Any Information", result.rightValue)
  }

  @Test
  fun `if the repository fails, returns the exception received`() {
    val lineId = 123
    val exception = ServiceException()
    given(lineDetailsRepository.getLineDetails(lineId)).willReturn(Either.Left(exception))

    val result = getLineInformation(lineId)

    verify(lineDetailsRepository).getLineDetails(lineId)
    assertTrue(result.isLeft)
    assertEquals(exception, result.leftValue)
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
