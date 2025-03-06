package org.galio.bussantiago.domain.interactor

import org.galio.bussantiago.domain.repository.LineDetailsRepository
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify

class GetLineDetailsImplTest {

  private val lineDetailsRepository = Mockito.mock(LineDetailsRepository::class.java)
  private val getLineDetails = GetLineDetailsImpl(lineDetailsRepository)

  @Test
  fun `invokes the line details repository`() {
    getLineDetails(123)

    verify(lineDetailsRepository).getLineDetails(123)
  }
}
