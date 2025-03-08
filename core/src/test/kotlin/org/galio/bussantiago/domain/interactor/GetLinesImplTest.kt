package org.galio.bussantiago.domain.interactor

import org.galio.bussantiago.data.repository.LineRepository
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class GetLinesImplTest {

  private val lineRepository = mock(LineRepository::class.java)
  private val getLines = GetLinesImpl(lineRepository)

  @Test
  fun `invokes the line repository`() {
    getLines(Unit)

    verify(lineRepository).getLines()
  }
}
