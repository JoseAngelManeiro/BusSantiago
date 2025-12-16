package org.galio.bussantiago.features.incidences

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.core.GetLineIncidences
import org.galio.bussantiago.core.model.Incidence
import org.galio.bussantiago.navigation.NavScreen
import org.galio.bussantiago.util.TestUseCaseExecutor
import org.galio.bussantiago.util.mock
import org.galio.bussantiago.util.thenFailure
import org.galio.bussantiago.util.thenSuccess
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever

class IncidencesViewModelTest {

  @get:Rule
  var rule: TestRule = InstantTaskExecutorRule()

  private val executor = TestUseCaseExecutor()
  private val getLineIncidences = mock<GetLineIncidences>()
  private val incidencesObserver = mock<Observer<Resource<List<Incidence>>>>()
  private val navEventObserver = mock<Observer<NavScreen>>()

  private lateinit var viewModel: IncidencesViewModel

  private val lineId = 123

  @Before
  fun setUp() {
    viewModel = IncidencesViewModel(executor, getLineIncidences)
    viewModel.incidences.observeForever(incidencesObserver)
    viewModel.navigationEvent.observeForever(navEventObserver)
  }

  @Test
  fun `if all goes well, the incidences are loaded as expected`() {
    val incidence1 = mock<Incidence>()
    val incidence2 = mock<Incidence>()
    val incidence3 = mock<Incidence>()
    whenever(getLineIncidences(lineId)).thenSuccess(listOf(incidence1, incidence2, incidence3))

    viewModel.loadIncidences(lineId)

    verify(incidencesObserver).onChanged(Resource.loading())
    verify(incidencesObserver).onChanged(Resource.success(listOf(incidence1, incidence2, incidence3)))
  }

  @Test
  fun `fire the exception received`() {
    val exception = Exception("Fake exception")
    whenever(getLineIncidences(lineId)).thenFailure(exception)

    viewModel.loadIncidences(lineId)

    verify(incidencesObserver).onChanged(Resource.loading())
    verify(incidencesObserver).onChanged(Resource.error(exception))
  }

  @Test
  fun `when cancel button is clicked should exit`() {
    viewModel.onCancelButtonClicked()

    verify(navEventObserver).onChanged(NavScreen.Exit)
  }
}
