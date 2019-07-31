package org.galio.bussantiago.ui.incidences

import org.galio.bussantiago.common.Either
import org.galio.bussantiago.common.exception.ServiceException
import org.galio.bussantiago.domain.model.Incidence
import org.galio.bussantiago.domain.model.LineDetails
import org.galio.bussantiago.domain.repository.LineDetailsRepository
import org.galio.bussantiago.util.mock
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.mockito.Mockito.verify

class GetLineIncidencesTest {

    private val lineDetailsRepository = Mockito.mock(LineDetailsRepository::class.java)
    private val getLineIncidences = GetLineIncidences(lineDetailsRepository)

    @Test
    fun `invokes repository and returns the incidences`() {
        val lineIdStub = 123
        val incidencesExpected = mock<List<Incidence>>()
        val lineDetailsStub = createLineDetails(incidencesExpected)
        given(lineDetailsRepository.getLineDetails(lineIdStub))
            .willReturn(Either.Right(lineDetailsStub))

        val result = getLineIncidences(lineIdStub)

        verify(lineDetailsRepository).getLineDetails(lineIdStub)
        assertTrue(result.isRight)
        assertEquals(incidencesExpected, result.rightValue)
    }

    @Test
    fun `if the repository fails, returns the exception received`() {
        val lineIdStub = 123
        val exceptionExpected = ServiceException()
        given(lineDetailsRepository.getLineDetails(lineIdStub))
            .willReturn(Either.Left(exceptionExpected))

        val result = getLineIncidences(lineIdStub)

        verify(lineDetailsRepository).getLineDetails(lineIdStub)
        assertTrue(result.isLeft)
        assertEquals(exceptionExpected, result.leftValue)
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
