package org.galio.bussantiago.features.times

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.core.AddBusStopFavorite
import org.galio.bussantiago.core.GetBusStopRemainingTimes
import org.galio.bussantiago.core.RemoveBusStopFavorite
import org.galio.bussantiago.core.ValidateIfBusStopIsFavorite
import org.galio.bussantiago.core.model.BusStopFavorite
import org.galio.bussantiago.core.model.BusStopRemainingTimes
import org.galio.bussantiago.shared.LineRemainingTimeModel
import org.galio.bussantiago.shared.TimesFactory
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

class TimesViewModelTest {

  companion object {
    private const val busStopCode = "1234"
    private const val busStopName = "BusStop Name"
  }

  @get:Rule
  var rule: TestRule = InstantTaskExecutorRule()

  private val getBusStopRemainingTimes = mock<GetBusStopRemainingTimes>()
  private val validateIfBusStopIsFavorite = mock<ValidateIfBusStopIsFavorite>()
  private val addBusStopFavorite = mock<AddBusStopFavorite>()
  private val removeBusStopFavorite = mock<RemoveBusStopFavorite>()
  private val timesFactory = mock<TimesFactory>()
  private val timesObserver = mock<Observer<Resource<List<LineRemainingTimeModel>>>>()
  private val favoriteObserver = mock<Observer<Boolean>>()

  private lateinit var viewModel: TimesViewModel

  @Before
  fun setUp() {
    viewModel = TimesViewModel(
      executor = TestUseCaseExecutor(),
      getBusStopRemainingTimes,
      validateIfBusStopIsFavorite,
      addBusStopFavorite,
      removeBusStopFavorite,
      timesFactory
    )
    viewModel.setArgs(busStopCode, busStopName)
  }

  @Test
  fun `the line remaining time models are loaded correctly`() {
    val busStopRemainingTimesStub = mock<BusStopRemainingTimes>()
    val lineRemainingTimeModelsStub = mock<List<LineRemainingTimeModel>>()
    whenever(getBusStopRemainingTimes(busStopCode))
      .thenSuccess(busStopRemainingTimesStub)
    whenever(timesFactory.createLineRemainingTimeModels(busStopRemainingTimesStub))
      .thenReturn(lineRemainingTimeModelsStub)
    viewModel.lineRemainingTimeModels.observeForever(timesObserver)

    viewModel.loadTimes()

    verify(timesObserver).onChanged(Resource.loading())
    verify(timesObserver).onChanged(Resource.success(lineRemainingTimeModelsStub))
  }

  @Test
  fun `fire the exception received when load times fail`() {
    val exception = mock<Exception>()
    whenever(getBusStopRemainingTimes(busStopCode)).thenFailure(exception)
    viewModel.lineRemainingTimeModels.observeForever(timesObserver)

    viewModel.loadTimes()

    verify(timesObserver).onChanged(Resource.loading())
    verify(timesObserver).onChanged(Resource.error(exception))
  }

  @Test
  fun `after validating if the stop is favorite, the status will be updated`() {
    whenever(validateIfBusStopIsFavorite(busStopCode)).thenSuccess(true)
    viewModel.isFavorite.observeForever(favoriteObserver)

    viewModel.validateBusStop()

    verify(favoriteObserver).onChanged(true)
  }

  @Test
  fun `when changing the status to favorite, we add the stop to favorites`() {
    initFavoriteState(false)
    val request = BusStopFavorite(busStopCode, busStopName)
    whenever(addBusStopFavorite(request)).thenSuccess(Unit)
    viewModel.isFavorite.observeForever(favoriteObserver)

    viewModel.changeFavoriteState()

    verify(favoriteObserver).onChanged(true)
    verify(addBusStopFavorite).invoke(request)
  }

  @Test
  fun `when changing the status to not favorite, we remove the stop from favorites`() {
    initFavoriteState(true)
    val request = BusStopFavorite(busStopCode, busStopName)
    whenever(removeBusStopFavorite(request)).thenSuccess(Unit)
    viewModel.isFavorite.observeForever(favoriteObserver)

    viewModel.changeFavoriteState()

    verify(favoriteObserver).onChanged(false)
    verify(removeBusStopFavorite).invoke(request)
  }

  private fun initFavoriteState(state: Boolean) {
    whenever(validateIfBusStopIsFavorite(busStopCode)).thenSuccess(state)
    viewModel.validateBusStop()
  }
}
