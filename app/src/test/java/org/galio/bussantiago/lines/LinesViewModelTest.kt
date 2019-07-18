package org.galio.bussantiago.lines

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import org.galio.bussantiago.common.Either
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.executor.SyncInteractorExecutor
import org.galio.bussantiago.domain.model.Line
import org.galio.bussantiago.util.mock
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

class LinesViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val executor = SyncInteractorExecutor()
    private val getLines = mock<GetLines>()
    private val observer = mock<Observer<Resource<List<LineModel>>>>()

    private lateinit var linesViewModel: LinesViewModel

    private val lines = listOf(createLineStub())
    private val lineViews = listOf(createLineViewStub())

    @Test
    fun `load the expected list of lines`() {
        `when`(getLines(Unit)).thenReturn(Either.Right(lines))

        linesViewModel = LinesViewModel(executor, getLines)
        linesViewModel.lines.observeForever(observer)

        verify(observer).onChanged(Resource.success(lineViews))
    }

    @Test
    fun `fire the exception received`() {
        val exception = Exception("Fake exception")
        `when`(getLines(Unit)).thenReturn(Either.Left(exception))

        linesViewModel = LinesViewModel(executor, getLines)
        linesViewModel.lines.observeForever(observer)

        verify(observer).onChanged(Resource.error(exception))
    }

    private fun createLineStub(): Line {
        return Line(
          id = 1,
          code = "Any code",
          synoptic = "Any Synoptic",
          name = "Any name",
          company = "Any company",
          incidents = 0,
          style = "Any color"
        )
    }

    private fun createLineViewStub(): LineModel {
        return LineModel(
            id = 1,
            synopticModel = SynopticModel(
              synoptic = "Any Synoptic",
              style = "Any color"
            ),
            name = "Any name"
        )
    }
}
