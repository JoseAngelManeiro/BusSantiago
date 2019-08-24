package org.galio.bussantiago.features.times

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.galio.bussantiago.common.Either
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.exception.NetworkConnectionException
import org.galio.bussantiago.common.executor.SyncInteractorExecutor
import org.galio.bussantiago.domain.model.BusStopFavorite
import org.galio.bussantiago.domain.model.BusStopRemainingTimes
import org.galio.bussantiago.util.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

class TimesViewModelTest {

  companion object {
    private const val busStopCode = "1234"
    private const val busStopName = "BusStop Name"
  }

  @get:Rule
  var rule: TestRule = InstantTaskExecutorRule()

  private val executor = SyncInteractorExecutor()
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
      executor,
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
    `when`(getBusStopRemainingTimes(busStopCode))
      .thenReturn(Either.Right(busStopRemainingTimesStub))
    `when`(timesFactory.createLineRemainingTimeModels(busStopRemainingTimesStub))
      .thenReturn(lineRemainingTimeModelsStub)
    viewModel.lineRemainingTimeModels.observeForever(timesObserver)

    viewModel.loadTimes()

    verify(timesObserver).onChanged(Resource.loading())
    verify(timesObserver).onChanged(Resource.success(lineRemainingTimeModelsStub))
  }

  @Test
  fun `fire the exception received when load times fail`() {
    val exceptionStub = NetworkConnectionException()
    `when`(getBusStopRemainingTimes(busStopCode)).thenReturn(Either.Left(exceptionStub))
    viewModel.lineRemainingTimeModels.observeForever(timesObserver)

    viewModel.loadTimes()

    verify(timesObserver).onChanged(Resource.loading())
    verify(timesObserver).onChanged(Resource.error(exceptionStub))
  }

  @Test
  fun `after validating if the stop is favorite, the status will be updated`() {
    `when`(validateIfBusStopIsFavorite(busStopCode)).thenReturn(Either.Right(true))
    viewModel.isFavorite.observeForever(favoriteObserver)

    viewModel.validateBusStop()

    verify(favoriteObserver).onChanged(true)
  }

  @Test
  fun `when changing the status to favorite, we add the stop to favorites`() {
    initFavoriteState(false)
    val request = BusStopFavorite(busStopCode, busStopName)
    `when`(addBusStopFavorite(request)).thenReturn(Either.Right(Unit))
    viewModel.isFavorite.observeForever(favoriteObserver)

    viewModel.changeFavoriteState()

    verify(favoriteObserver).onChanged(true)
    verify(addBusStopFavorite).invoke(request)
  }

  @Test
  fun `when changing the status to not favorite, we remove the stop from favorites`() {
    initFavoriteState(true)
    val request = BusStopFavorite(busStopCode, busStopName)
    `when`(removeBusStopFavorite(request)).thenReturn(Either.Right(Unit))
    viewModel.isFavorite.observeForever(favoriteObserver)

    viewModel.changeFavoriteState()

    verify(favoriteObserver).onChanged(false)
    verify(removeBusStopFavorite).invoke(request)
  }

  private fun initFavoriteState(state: Boolean) {
    `when`(validateIfBusStopIsFavorite(busStopCode)).thenReturn(Either.Right(state))
    viewModel.validateBusStop()
  }
}
