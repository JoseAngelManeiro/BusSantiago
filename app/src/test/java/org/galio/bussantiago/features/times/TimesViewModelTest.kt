package org.galio.bussantiago.features.times

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.galio.bussantiago.common.Either
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.exception.NetworkConnectionException
import org.galio.bussantiago.common.executor.SyncInteractorExecutor
import org.galio.bussantiago.domain.model.BusStopRemainingTimes
import org.galio.bussantiago.util.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

class TimesViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val executor = SyncInteractorExecutor()
    private val getBusStopRemainingTimes = mock<GetBusStopRemainingTimes>()
    private val timesFactory = mock<TimesFactory>()
    private val observer = mock<Observer<Resource<List<LineRemainingTimeModel>>>>()

    private lateinit var viewModel: TimesViewModel

    @Before
    fun setUp() {
        viewModel = TimesViewModel(executor, getBusStopRemainingTimes, timesFactory)
        viewModel.lineRemainingTimeModels.observeForever(observer)
    }

    @Test
    fun `if all goes well, the line remaining time models are loaded correctly`() {
        val busStopCode = "1234"
        val busStopRemainingTimesStub = mock<BusStopRemainingTimes>()
        val lineRemainingTimeModelsStub = mock<List<LineRemainingTimeModel>>()
        `when`(getBusStopRemainingTimes(busStopCode))
            .thenReturn(Either.Right(busStopRemainingTimesStub))
        `when`(timesFactory.createLineRemainingTimeModels(busStopRemainingTimesStub))
            .thenReturn(lineRemainingTimeModelsStub)

        viewModel.loadRemainingTimes(busStopCode)

        verify(observer).onChanged(Resource.loading())
        verify(observer).onChanged(Resource.success(lineRemainingTimeModelsStub))
    }

    @Test
    fun `fire the exception received`() {
        val busStopCode = "1234"
        val exceptionStub = NetworkConnectionException()
        `when`(getBusStopRemainingTimes(busStopCode)).thenReturn(Either.Left(exceptionStub))

        viewModel.loadRemainingTimes(busStopCode)

        verify(observer).onChanged(Resource.loading())
        verify(observer).onChanged(Resource.error(exceptionStub))
    }
}
