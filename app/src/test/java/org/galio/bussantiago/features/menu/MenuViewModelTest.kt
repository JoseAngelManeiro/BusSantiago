package org.galio.bussantiago.features.menu

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.core.GetLineDetails
import org.galio.bussantiago.core.model.LineDetails
import org.galio.bussantiago.framework.analytics.AnalyticsEvents
import org.galio.bussantiago.framework.analytics.AnalyticsParams
import org.galio.bussantiago.framework.analytics.AnalyticsTracker
import org.galio.bussantiago.framework.analytics.Screens
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

// See also: MenuOptionClickParameterizedTest.kt for parameterized test cases
class MenuViewModelTest {

  @get:Rule
  var rule: TestRule = InstantTaskExecutorRule()

  private val executor = TestUseCaseExecutor()
  private val getLineDetails = mock<GetLineDetails>()
  private val menuFactory = mock<MenuFactory>()
  private val analyticsTracker = mock<AnalyticsTracker>()
  private val menuObserver = mock<Observer<Resource<MenuModel>>>()

  private val viewModel = MenuViewModel(executor, getLineDetails, menuFactory, analyticsTracker)

  @Before
  fun setUp() {
    viewModel.menuModel.observeForever(menuObserver)
  }

  @Test
  fun `should load the data correctly`() {
    val lineId = 123
    val lineDetailsStub = mock<LineDetails>()
    val menuModelStub = mock<MenuModel>()
    whenever(getLineDetails(lineId)).thenSuccess(lineDetailsStub)
    whenever(menuFactory.createMenu(lineDetailsStub)).thenReturn(menuModelStub)

    viewModel.loadLineDetails(lineId)

    verify(menuObserver).onChanged(Resource.loading())
    verify(menuObserver).onChanged(Resource.success(menuModelStub))
  }

  @Test
  fun `should fire the exception received`() {
    val lineId = 123
    val exception = Exception("Fake exception")
    whenever(getLineDetails(lineId)).thenFailure(exception)

    viewModel.loadLineDetails(lineId)

    verify(menuObserver).onChanged(Resource.loading())
    verify(menuObserver).onChanged(Resource.error(exception))
  }

  @Test
  fun `when init is called should track the Menu screen`() {
    val lineId = 123
    val lineDetailsStub = mock<LineDetails>()
    whenever(getLineDetails(lineId)).thenSuccess(lineDetailsStub)
    whenever(menuFactory.createMenu(lineDetailsStub)).thenReturn(mock())

    viewModel.init(lineId)

    verify(analyticsTracker).trackScreen(Screens.MENU)
  }

  @Test
  fun `when a menu option is clicked should track the select line route event`() {
    val lineId = 123
    val menuOptionModel = MenuOptionModel(MenuType.OUTWARD_ROUTE, "Outward")

    viewModel.onMenuOptionClicked(menuOptionModel, lineId)

    verify(analyticsTracker).trackEvent(
      AnalyticsEvents.SELECT_LINE_ROUTE,
      mapOf(
        AnalyticsParams.LINE_ID to lineId,
        AnalyticsParams.MENU_OPTION to "Outward"
      )
    )
  }
}
