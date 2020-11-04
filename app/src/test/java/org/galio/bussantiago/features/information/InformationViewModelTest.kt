package org.galio.bussantiago.features.information

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.galio.bussantiago.Either
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.executor.SyncInteractorExecutor
import org.galio.bussantiago.domain.interactor.GetLineInformation
import org.galio.bussantiago.util.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

class InformationViewModelTest {

  @get:Rule
  var rule: TestRule = InstantTaskExecutorRule()

  private val executor = SyncInteractorExecutor()
  private val getLineInformation = mock<GetLineInformation>()
  private val observer = mock<Observer<Resource<String>>>()

  private lateinit var viewModel: InformationViewModel

  private val lineId = 123

  @Before
  fun setUp() {
    viewModel = InformationViewModel(executor, getLineInformation)
    viewModel.setArgs(lineId)
    viewModel.information.observeForever(observer)
  }

  @Test
  fun `if all goes well, the data is loaded correctly`() {
    val lineInformationStub = "Any Information"
    `when`(getLineInformation(lineId)).thenReturn(Either.Right(lineInformationStub))

    viewModel.loadLineInformation()

    verify(observer).onChanged(Resource.loading())
    verify(observer).onChanged(Resource.success("Any Information"))
  }

  @Test
  fun `fire the exception received`() {
    val exception = Exception("Fake exception")
    `when`(getLineInformation(lineId)).thenReturn(Either.Left(exception))

    viewModel.loadLineInformation()

    verify(observer).onChanged(Resource.loading())
    verify(observer).onChanged(Resource.error(exception))
  }
}
