package org.galio.bussantiago.ui.information

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.galio.bussantiago.common.Either
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.executor.SyncInteractorExecutor
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

    @Before
    fun setUp() {
        viewModel = InformationViewModel(executor, getLineInformation)
        viewModel.informationModel.observeForever(observer)
    }

    @Test
    fun `if all goes well, the data is loaded correctly`() {
        val lineIdStub = 123
        val lineInformationStub = "Any Information"
        `when`(getLineInformation(lineIdStub)).thenReturn(Either.Right(lineInformationStub))

        viewModel.loadLineInformation(lineIdStub)

        verify(observer).onChanged(Resource.loading())
        verify(observer).onChanged(Resource.success(lineInformationStub))
    }

    @Test
    fun `fire the exception received`() {
        val lineIdStub = 123
        val exception = Exception("Fake exception")
        `when`(getLineInformation(lineIdStub)).thenReturn(Either.Left(exception))

        viewModel.loadLineInformation(lineIdStub)

        verify(observer).onChanged(Resource.loading())
        verify(observer).onChanged(Resource.error(exception))
    }
}