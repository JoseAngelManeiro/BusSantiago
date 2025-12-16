package org.galio.bussantiago.features.incidences

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.core.GetLineIncidences
import org.galio.bussantiago.core.model.Incidence
import org.galio.bussantiago.navigation.NavScreen
import org.galio.bussantiago.util.TestUseCaseExecutor
import org.galio.bussantiago.util.mock
import org.galio.bussantiago.util.thenFailure
import org.galio.bussantiago.util.thenSuccess
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.kotlin.whenever

class IncidencesViewModelTest {

  @get:Rule
  var rule: TestRule = InstantTaskExecutorRule()

  private val executor = TestUseCaseExecutor()
  private val getLineIncidences = mock<GetLineIncidences>()
  private val lineId = 123

  @Test
  fun `if all goes well, the incidences are loaded as expected`() {
    val incidence1 = mock<Incidence>()
    val incidence2 = mock<Incidence>()
    val incidence3 = mock<Incidence>()
    whenever(getLineIncidences(lineId)).thenSuccess(listOf(incidence1, incidence2, incidence3))

    val viewModel = createViewModel()

    assertEquals(Resource.success(listOf(incidence1, incidence2, incidence3)), viewModel.incidences.value)
  }

  @Test
  fun `fire the exception received`() {
    val exception = Exception("Fake exception")
    whenever(getLineIncidences(lineId)).thenFailure(exception)

    val viewModel = createViewModel()

    assertEquals(Resource.error<List<Incidence>>(exception), viewModel.incidences.value)
  }

  @Test
  fun `onRetry should reload incidences`() {
    val exception = Exception("Fake exception")
    whenever(getLineIncidences(lineId)).thenFailure(exception)

    val viewModel = createViewModel()

    assertEquals(Resource.error<List<Incidence>>(exception), viewModel.incidences.value)

    val incidence = mock<Incidence>()
    whenever(getLineIncidences(lineId)).thenSuccess(listOf(incidence))

    viewModel.onRetry()

    assertEquals(Resource.success(listOf(incidence)), viewModel.incidences.value)
  }

  @Test
  fun `onCancel should emit Exit navigation event`() {
    whenever(getLineIncidences(lineId)).thenSuccess(emptyList())

    val viewModel = createViewModel()
    viewModel.onCancel()

    assertEquals(NavScreen.Exit, viewModel.navigationEvent.value)
  }

  private fun createViewModel() = IncidencesViewModel(lineId, executor, getLineIncidences)
}
