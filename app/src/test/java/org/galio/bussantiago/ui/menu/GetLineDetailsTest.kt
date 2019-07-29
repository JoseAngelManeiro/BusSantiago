package org.galio.bussantiago.ui.menu

import org.galio.bussantiago.domain.repository.LineDetailsRepository
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify

class GetLineDetailsTest {

    private val lineDetailsRepository = Mockito.mock(LineDetailsRepository::class.java)
    private val getLineDetails = GetLineDetails(lineDetailsRepository)

    @Test
    fun `invokes the line details repository`() {
        val lineIdStub = 11

        getLineDetails(lineIdStub)

        verify(lineDetailsRepository).getLineDetails(lineIdStub)
    }
}
