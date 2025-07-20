package org.galio.bussantiago.features.lines

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.core.GetLines
import org.galio.bussantiago.core.model.Line
import org.galio.bussantiago.navigation.NavScreen
import org.galio.bussantiago.shared.SynopticModel
import org.galio.bussantiago.util.TestInteractorExecutor
import org.galio.bussantiago.util.mock
import org.galio.bussantiago.util.thenFailure
import org.galio.bussantiago.util.thenSuccess
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever

class LinesViewModelTest {

  @get:Rule
  var rule: TestRule = InstantTaskExecutorRule()

  private val executor = TestInteractorExecutor()
  private val getLines = mock<GetLines>()
  private val lineModelsObserver = mock<Observer<Resource<List<LineModel>>>>()
  private val navEventObserver = mock<Observer<NavScreen>>()

  private lateinit var linesViewModel: LinesViewModel

  @Before
  fun setUp() {
    linesViewModel = LinesViewModel(executor, getLines)
    linesViewModel.lineModels.observeForever(lineModelsObserver)
    linesViewModel.navigationEvent.observeForever(navEventObserver)
  }

  @Test
  fun `load the expected list of lines`() {
    val linesStub = listOf(createLineStub())
    whenever(getLines(Unit)).thenSuccess(linesStub)

    linesViewModel.loadLines()

    verify(lineModelsObserver).onChanged(Resource.loading())
    val lineViewsExpected = listOf(createLineViewStub())
    verify(lineModelsObserver).onChanged(Resource.success(lineViewsExpected))
  }

  @Test
  fun `fire the exception received`() {
    val exception = Exception("Fake exception")
    whenever(getLines(Unit)).thenFailure(exception)

    linesViewModel.loadLines()

    verify(lineModelsObserver).onChanged(Resource.loading())
    verify(lineModelsObserver).onChanged(Resource.error(exception))
  }

  @Test
  fun `when line is clicked should navigate to screen expected`() {
    linesViewModel.onLineClicked(53)

    verify(navEventObserver).onChanged(NavScreen.LineMenu(53))
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
      name = "Any name",
      incidents = 0
    )
  }
}
