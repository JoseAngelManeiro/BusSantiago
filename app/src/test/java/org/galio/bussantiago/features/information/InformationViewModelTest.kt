package org.galio.bussantiago.features.information

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.core.Either
import org.galio.bussantiago.core.GetLineInformation
import org.galio.bussantiago.util.TestInteractorExecutor
import org.galio.bussantiago.util.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class InformationViewModelTest {

  @get:Rule
  var rule: TestRule = InstantTaskExecutorRule()

  private val executor = TestInteractorExecutor()
  private val getLineInformation = mock<GetLineInformation>()
  private val observer = mock<Observer<Resource<String>>>()

  private lateinit var viewModel: InformationViewModel

  private val lineId = 123

  @Before
  fun setUp() {
    viewModel = InformationViewModel(executor, getLineInformation)
    viewModel.information.observeForever(observer)
  }

  @Test
  fun `if all goes well, the data is loaded correctly`() {
    val lineInformationStub = "Any Information"
    `when`(getLineInformation(lineId)).thenReturn(Either.Success(lineInformationStub))

    viewModel.loadLineInformation(lineId)

    verify(observer).onChanged(Resource.loading())
    verify(observer).onChanged(Resource.success("Any Information"))
  }

  @Test
  fun `fire the exception received`() {
    val exception = Exception("Fake exception")
    `when`(getLineInformation(lineId)).thenReturn(Either.Error(exception))

    viewModel.loadLineInformation(lineId)

    verify(observer).onChanged(Resource.loading())
    verify(observer).onChanged(Resource.error(exception))
  }
}
