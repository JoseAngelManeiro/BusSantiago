package org.galio.bussantiago.features.stops

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.galio.bussantiago.Either
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.executor.SyncInteractorExecutor
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.domain.interactor.GetLineBusStops
import org.galio.bussantiago.domain.model.BusStop
import org.galio.bussantiago.util.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

class BusStopsViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val executor = SyncInteractorExecutor()
    private val getLineBusStops = mock<GetLineBusStops>()
    private val observer = mock<Observer<Resource<List<BusStopModel>>>>()

    private lateinit var viewModel: BusStopsViewModel

    @Before
    fun setUp() {
        viewModel = BusStopsViewModel(executor, getLineBusStops)
        viewModel.busStopModels.observeForever(observer)
    }

    @Test
    fun `if all goes well, the bus stop models are mapped and loaded correctly`() {
        val lineId = 123
        val routeName = "Any route name"
        val request = GetLineBusStops.Request(lineId, routeName)
        val busStopsStub = listOf(createBusStop(code = "1234", name = "Bus Stop 1"))
        `when`(getLineBusStops(request)).thenReturn(Either.Right(busStopsStub))

        viewModel.loadBusStops(lineId, routeName)

        verify(observer).onChanged(Resource.loading())
        verify(observer).onChanged(Resource.success(
            listOf(BusStopModel(code = "1234", name = "Bus Stop 1")))
        )
    }

    @Test
    fun `fire the exception received`() {
        val lineId = 123
        val routeName = "Any route name"
        val request = GetLineBusStops.Request(lineId, routeName)
        val exception = Exception("Fake exception")
        `when`(getLineBusStops(request)).thenReturn(Either.Left(exception))

        viewModel.loadBusStops(lineId, routeName)

        verify(observer).onChanged(Resource.loading())
        verify(observer).onChanged(Resource.error(exception))
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
