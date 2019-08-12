package org.galio.bussantiago.features.menu

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.galio.bussantiago.common.Either
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.executor.SyncInteractorExecutor
import org.galio.bussantiago.domain.model.LineDetails
import org.galio.bussantiago.util.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

class MenuViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val executor = SyncInteractorExecutor()
    private val getLineDetails = mock<GetLineDetails>()
    private val menuFactory = mock<MenuFactory>()
    private val observer = mock<Observer<Resource<MenuModel>>>()

    private lateinit var viewModel: MenuViewModel

    @Before
    fun setUp() {
        viewModel = MenuViewModel(executor, getLineDetails, menuFactory)
        viewModel.menuModel.observeForever(observer)
    }

    @Test
    fun `if all goes well, the data is loaded correctly`() {
        val lineId = 123
        val lineDetailsStub = mock<LineDetails>()
        val menuModelStub = mock<MenuModel>()
        `when`(getLineDetails(lineId)).thenReturn(Either.Right(lineDetailsStub))
        `when`(menuFactory.createMenu(lineDetailsStub)).thenReturn(menuModelStub)

        viewModel.loadLineDetails(lineId)

        verify(observer).onChanged(Resource.loading())
        verify(observer).onChanged(Resource.success(menuModelStub))
    }

    @Test
    fun `fire the exception received`() {
        val lineId = 123
        val exception = Exception("Fake exception")
        `when`(getLineDetails(lineId)).thenReturn(Either.Left(exception))

        viewModel.loadLineDetails(lineId)

        verify(observer).onChanged(Resource.loading())
        verify(observer).onChanged(Resource.error(exception))
    }
}
