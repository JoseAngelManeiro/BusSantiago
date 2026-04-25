package org.galio.bussantiago.features.information

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.core.GetLineInformation
import org.galio.bussantiago.navigation.NavScreen
import org.galio.bussantiago.util.TestUseCaseExecutor
import org.galio.bussantiago.util.mock
import org.galio.bussantiago.util.thenFailure
import org.galio.bussantiago.util.thenSuccess
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.atLeastOnce
import org.mockito.Mockito.verify
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

class InformationViewModelTest {

  @get:Rule
  var rule: TestRule = InstantTaskExecutorRule()

  private val executor = TestUseCaseExecutor()
  private val getLineInformation = mock<GetLineInformation>()
  private val observer = mock<Observer<Resource<String>>>()
  private val navigationObserver = mock<Observer<NavScreen>>()

  private val lineId = 123

  private fun createViewModel() = InformationViewModel(lineId, executor, getLineInformation)

  @Test
  fun `if all goes well, the data is loaded correctly on initialization`() {
    val lineInformationStub = "Any Information"
    whenever(getLineInformation(lineId)).thenSuccess(lineInformationStub)

    val viewModel = createViewModel()
    viewModel.information.observeForever(observer)

    verify(observer).onChanged(Resource.success("Any Information"))
  }

  @Test
  fun `fire the exception received on initialization`() {
    val exception = Exception("Fake exception")
    whenever(getLineInformation(lineId)).thenFailure(exception)

    val viewModel = createViewModel()
    viewModel.information.observeForever(observer)

    verify(observer).onChanged(Resource.error(exception))
  }

  @Test
  fun `if retry, then data is loaded again`() {
    val lineInformationStub = "Any Information"
    whenever(getLineInformation(lineId)).thenSuccess(lineInformationStub)

    val viewModel = createViewModel()
    viewModel.onRetry()
    viewModel.information.observeForever(observer)

    verify(observer, atLeastOnce()).onChanged(Resource.success("Any Information"))
  }

  @Test
  fun `if cancel, then navigate back`() {
    whenever(getLineInformation(any())).thenSuccess("stub")
    val viewModel = createViewModel()
    viewModel.navigationEvent.observeForever(navigationObserver)

    viewModel.onCancel()

    verify(navigationObserver).onChanged(NavScreen.Exit)
  }
}
