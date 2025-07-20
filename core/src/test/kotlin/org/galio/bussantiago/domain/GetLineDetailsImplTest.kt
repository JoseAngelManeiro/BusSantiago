package org.galio.bussantiago.domain

import org.galio.bussantiago.data.repository.LineDetailsRepository
import org.galio.bussantiago.util.mock
import org.junit.Test
import org.mockito.Mockito.verify

class GetLineDetailsImplTest {

  private val lineDetailsRepository = mock<LineDetailsRepository>()
  private val getLineDetails = GetLineDetailsImpl(lineDetailsRepository)

  @Test
  fun `invokes the line details repository`() {
    getLineDetails(123)

    verify(lineDetailsRepository).getLineDetails(123)
  }
}
