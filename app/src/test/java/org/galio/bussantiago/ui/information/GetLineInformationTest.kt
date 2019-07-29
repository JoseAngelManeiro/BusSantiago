package org.galio.bussantiago.ui.information

import org.galio.bussantiago.common.Either
import org.galio.bussantiago.common.exception.ServiceException
import org.galio.bussantiago.domain.model.LineDetails
import org.galio.bussantiago.domain.repository.LineDetailsRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.Mockito

class GetLineInformationTest {

    private val lineDetailsRepository = Mockito.mock(LineDetailsRepository::class.java)
    private val getLineInformation = GetLineInformation(lineDetailsRepository)

    @Test
    fun `invokes repository and returns the information field`() {
        val lineIdStub = 123
        val informationExpected = "Any Information"
        val lineDetailsStub = createLineDetails(informationExpected)
        given(lineDetailsRepository.getLineDetails(lineIdStub))
            .willReturn(Either.Right(lineDetailsStub))

        val result = getLineInformation(lineIdStub)

        verify(lineDetailsRepository).getLineDetails(lineIdStub)
        assertTrue(result.isRight)
        assertEquals(informationExpected, result.rightValue)
    }

    @Test
    fun `if the repository fails, returns the exception received`() {
        val lineIdStub = 123
        val exceptionExpected = ServiceException()
        given(lineDetailsRepository.getLineDetails(lineIdStub))
            .willReturn(Either.Left(exceptionExpected))

        val result = getLineInformation(lineIdStub)

        verify(lineDetailsRepository).getLineDetails(lineIdStub)
        assertTrue(result.isLeft)
        assertEquals(exceptionExpected, result.leftValue)
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
