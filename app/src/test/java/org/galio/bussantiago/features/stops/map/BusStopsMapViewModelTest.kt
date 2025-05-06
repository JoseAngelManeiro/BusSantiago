package org.galio.bussantiago.features.stops.map

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.core.Either
import org.galio.bussantiago.core.GetLineDetails
import org.galio.bussantiago.core.model.LineDetails
import org.galio.bussantiago.features.stops.BusStopsArgs
import org.galio.bussantiago.navigation.NavScreen
import org.galio.bussantiago.navigation.Navigator
import org.galio.bussantiago.util.TestInteractorExecutor
import org.galio.bussantiago.util.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.never

class BusStopsMapViewModelTest {

  @get:Rule
  var rule: TestRule = InstantTaskExecutorRule()

  private val executor = TestInteractorExecutor()
  private val getLineDetails = mock<GetLineDetails>()
  private val lineMapModelFactory = mock<LineMapModelFactory>()
  private val observer = mock<Observer<Resource<LineMapModel>>>()
  private val navigator = mock<Navigator>()

  private lateinit var viewModel: BusStopsMapViewModel

  @Before
  fun setUp() {
    viewModel = BusStopsMapViewModel(executor, getLineDetails, lineMapModelFactory, navigator)
    viewModel.lineMapModel.observeForever(observer)
  }

  @Test
  fun `should invokes the factory and load the result correctly`() {
    val busStopsArgs = BusStopsArgs(lineId = 123, routeName = "Route 1")
    val lineDetailsStub = mock<LineDetails>()
    given(getLineDetails.invoke(123))
      .willReturn(Either.Success(lineDetailsStub))
    val lineMapModelStub = mock<LineMapModel>()
    given(lineMapModelFactory.createLineMapModelFactory("Route 1", lineDetailsStub))
      .willReturn(lineMapModelStub)

    viewModel.load(busStopsArgs)

    verify(observer).onChanged(Resource.success(lineMapModelStub))
  }

  @Test
  fun `should fire the exception received`() {
    val busStopsArgs = BusStopsArgs(lineId = 123, routeName = "Route 1")
    val exceptionStub = mock<Exception>()
    given(getLineDetails.invoke(123))
      .willReturn(Either.Error(exceptionStub))

    viewModel.load(busStopsArgs)

    verify(observer).onChanged(Resource.error(exceptionStub))
  }

  @Test
  fun `when info window is clicked should navigate to expected screen`() {
    val markerTitle = "1"
    val markerDescription = "Os Tilos"

    viewModel.onInfoWindowClick(markerTitle, markerDescription)

    verify(navigator).navigate(NavScreen.Times(BusStopModel(markerTitle, markerDescription)))
  }

  @Test
  fun `when info window is clicked and title is null should not navigate`() {
    val markerTitle = null
    val markerDescription = "Os Tilos"

    viewModel.onInfoWindowClick(markerTitle, markerDescription)

    verify(navigator, never()).navigate(anyOrNull())
  }

  @Test
  fun `when info window is clicked and description is null should not navigate`() {
    val markerTitle = "1"
    val markerDescription = null

    viewModel.onInfoWindowClick(markerTitle, markerDescription)

    verify(navigator, never()).navigate(anyOrNull())
  }
}
