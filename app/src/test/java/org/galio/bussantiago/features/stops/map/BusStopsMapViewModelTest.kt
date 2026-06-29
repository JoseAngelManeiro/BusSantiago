package org.galio.bussantiago.features.stops.map

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.core.GetLineDetails
import org.galio.bussantiago.core.model.LineDetails
import org.galio.bussantiago.features.stops.BusStopsArgs
import org.galio.bussantiago.framework.analytics.AnalyticsEvents
import org.galio.bussantiago.framework.analytics.AnalyticsParams
import org.galio.bussantiago.framework.analytics.AnalyticsTracker
import org.galio.bussantiago.framework.analytics.Screens
import org.galio.bussantiago.navigation.NavScreen
import org.galio.bussantiago.util.TestUseCaseExecutor
import org.galio.bussantiago.util.mock
import org.galio.bussantiago.util.thenFailure
import org.galio.bussantiago.util.thenSuccess
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.BDDMockito.verify
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.never
import org.mockito.kotlin.whenever

class BusStopsMapViewModelTest {

  @get:Rule
  var rule: TestRule = InstantTaskExecutorRule()

  private val executor = TestUseCaseExecutor()
  private val getLineDetails = mock<GetLineDetails>()
  private val lineMapModelFactory = mock<LineMapModelFactory>()
  private val analyticsTracker = mock<AnalyticsTracker>()
  private val lineMapObserver = mock<Observer<Resource<LineMapModel>>>()
  private val navEventObserver = mock<Observer<NavScreen>>()

  private lateinit var viewModel: BusStopsMapViewModel

  @Before
  fun setUp() {
    viewModel = BusStopsMapViewModel(executor, getLineDetails, lineMapModelFactory, analyticsTracker)
    viewModel.lineMapModel.observeForever(lineMapObserver)
    viewModel.navigationEvent.observeForever(navEventObserver)
  }

  @Test
  fun `should invokes the factory and load the result correctly`() {
    val busStopsArgs = BusStopsArgs(lineId = 123, routeName = "Route 1")
    val lineDetailsStub = mock<LineDetails>()
    whenever(getLineDetails.invoke(123)).thenSuccess(lineDetailsStub)
    val lineMapModelStub = mock<LineMapModel>()
    whenever(lineMapModelFactory.createLineMapModelFactory("Route 1", lineDetailsStub))
      .thenReturn(lineMapModelStub)

    viewModel.load(busStopsArgs)

    verify(lineMapObserver).onChanged(Resource.success(lineMapModelStub))
  }

  @Test
  fun `when the use case is successful but the factory returns null should not update data`() {
    val busStopsArgs = BusStopsArgs(lineId = 123, routeName = "Route 1")
    val lineDetailsStub = mock<LineDetails>()
    whenever(getLineDetails.invoke(123)).thenSuccess(lineDetailsStub)
    whenever(lineMapModelFactory.createLineMapModelFactory("Route 1", lineDetailsStub))
      .thenReturn(null)

    viewModel.load(busStopsArgs)

    verify(lineMapObserver, never()).onChanged(any())
  }

  @Test
  fun `should fire the exception received`() {
    val busStopsArgs = BusStopsArgs(lineId = 123, routeName = "Route 1")
    val exceptionStub = mock<Exception>()
    whenever(getLineDetails.invoke(123)).thenFailure(exceptionStub)

    viewModel.load(busStopsArgs)

    verify(lineMapObserver).onChanged(Resource.error(exceptionStub))
  }

  @Test
  fun `when info window is clicked should navigate to expected screen`() {
    val markerTitle = "1"
    val markerDescription = "Os Tilos"

    viewModel.onInfoWindowClick(markerTitle, markerDescription)

    verify(navEventObserver).onChanged(
      NavScreen.Times(
        BusStopModel(
          markerTitle,
          markerDescription
        )
      )
    )
  }

  @Test
  fun `when info window is clicked should track select stop event`() {
    val markerTitle = "1"
    val markerDescription = "Os Tilos"

    viewModel.onInfoWindowClick(markerTitle, markerDescription)

    verify(analyticsTracker).trackEvent(
      AnalyticsEvents.SELECT_STOP,
      mapOf(
        AnalyticsParams.ORIGIN to Screens.LINE_STOPS_MAP,
        AnalyticsParams.STOP_CODE to "1",
        AnalyticsParams.STOP_NAME to "Os Tilos"
      )
    )
  }

  @Test
  fun `when info window is clicked and title is null should not navigate`() {
    val markerTitle = null
    val markerDescription = "Os Tilos"

    viewModel.onInfoWindowClick(markerTitle, markerDescription)

    verify(navEventObserver, never()).onChanged(anyOrNull())
  }

  @Test
  fun `when info window is clicked and description is null should not navigate`() {
    val markerTitle = "1"
    val markerDescription = null

    viewModel.onInfoWindowClick(markerTitle, markerDescription)

    verify(navEventObserver, never()).onChanged(anyOrNull())
  }
}
