package org.galio.bussantiago.features.stops.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.core.GetLineBusStops
import org.galio.bussantiago.core.model.BusStop
import org.galio.bussantiago.features.stops.BusStopsArgs
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

class BusStopsListViewModelTest {

  @get:Rule
  var rule: TestRule = InstantTaskExecutorRule()

  private val executor = TestUseCaseExecutor()
  private val getLineBusStops = mock<GetLineBusStops>()
  private val busStopObserver = mock<Observer<Resource<List<BusStopModel>>>>()
  private val navEventObserver = mock<Observer<NavScreen>>()

  private lateinit var viewModel: BusStopsListViewModel

  private val busStopsArgs = BusStopsArgs(lineId = 123, routeName = "Any route name")

  @Before
  fun setUp() {
    viewModel = BusStopsListViewModel(executor, getLineBusStops)
    viewModel.busStopModels.observeForever(busStopObserver)
    viewModel.navigationEvent.observeForever(navEventObserver)
  }

  @Test
  fun `if all goes well, the bus stop models are mapped and loaded correctly`() {
    val request = GetLineBusStops.Request(busStopsArgs.lineId, busStopsArgs.routeName)
    val busStopsStub = listOf(createBusStop(code = "1234", name = "Bus Stop 1"))
    whenever(getLineBusStops(request)).thenSuccess(busStopsStub)

    viewModel.loadBusStops(busStopsArgs)

    verify(busStopObserver).onChanged(Resource.loading())
    verify(busStopObserver).onChanged(
      Resource.success(listOf(BusStopModel(code = "1234", name = "Bus Stop 1")))
    )
  }

  @Test
  fun `fire the exception received`() {
    val request = GetLineBusStops.Request(busStopsArgs.lineId, busStopsArgs.routeName)
    val exception = Exception("Fake exception")
    whenever(getLineBusStops(request)).thenFailure(exception)

    viewModel.loadBusStops(busStopsArgs)

    verify(busStopObserver).onChanged(Resource.loading())
    verify(busStopObserver).onChanged(Resource.error(exception))
  }

  @Test
  fun `when bus stop is clicked should navigate to screen expected`() {
    val busStopModel = mock<BusStopModel>()

    viewModel.onBusStopClick(busStopModel)

    verify(navEventObserver).onChanged(NavScreen.Times(busStopModel))
  }

  private fun createBusStop(code: String, name: String): BusStop {
    return BusStop(
      id = 1,
      code = code,
      name = name,
      zone = null,
      extraordinary = false,
      coordinates = mock()
    )
  }
}
