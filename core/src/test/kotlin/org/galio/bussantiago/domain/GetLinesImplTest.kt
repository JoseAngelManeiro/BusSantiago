package org.galio.bussantiago.domain

import org.galio.bussantiago.data.repository.LineRepository
import org.galio.bussantiago.util.mock
import org.junit.Test
import org.mockito.Mockito.verify

class GetLinesImplTest {

  private val lineRepository = mock<LineRepository>()
  private val getLines = GetLinesImpl(lineRepository)

  @Test
  fun `invokes the line repository`() {
    getLines(Unit)

    verify(lineRepository).getLines()
  }
}
