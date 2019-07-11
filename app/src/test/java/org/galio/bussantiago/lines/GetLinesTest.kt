package org.galio.bussantiago.lines

import org.galio.bussantiago.domain.repository.LineRepository
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class GetLinesTest {

    private val lineRepository = mock(LineRepository::class.java)
    private val getLines = GetLines(lineRepository)

    @Test
    fun `invokes the line repository`() {
        getLines(Unit)

        verify(lineRepository).getLines()
    }
}
