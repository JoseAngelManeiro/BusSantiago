package org.galio.bussantiago.features.favorites

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.core.GetBusStopFavorites
import org.galio.bussantiago.core.model.BusStopFavorite
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
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever

class FavoritesViewModelTest {

  @get:Rule
  var rule: TestRule = InstantTaskExecutorRule()

  private val executor = TestUseCaseExecutor()
  private val getBusStopFavorites = mock<GetBusStopFavorites>()
  private val analyticsTracker = mock<AnalyticsTracker>()
  private val favoritesObserver = mock<Observer<Resource<List<BusStopFavorite>>>>()
  private val navEventObserver = mock<Observer<NavScreen>>()

  private lateinit var viewModel: FavoritesViewModel

  @Before
  fun setUp() {
    viewModel = FavoritesViewModel(executor, getBusStopFavorites, analyticsTracker)
    viewModel.favoriteModels.observeForever(favoritesObserver)
    viewModel.navigationEvent.observeForever(navEventObserver)
  }

  @Test
  fun `when init is called should track the Favorites screen`() {
    whenever(getBusStopFavorites()).thenSuccess(emptyList())

    viewModel.init()

    verify(analyticsTracker).trackScreen(Screens.FAVORITES)
  }

  @Test
  fun `when use case is invoked successfully should load data as expected`() {
    val favorites = listOf(mock<BusStopFavorite>())
    whenever(getBusStopFavorites()).thenSuccess(favorites)

    viewModel.init()

    verify(favoritesObserver).onChanged(Resource.success(favorites))
  }

  @Test
  fun `when use case fails should return exception receivedf`() {
    val exception = Exception("Fake exception")
    whenever(getBusStopFavorites()).thenFailure(exception)

    viewModel.init()

    verify(favoritesObserver).onChanged(Resource.error(exception))
  }

  @Test
  fun `when bus stop is clicked should navigate to screen expected`() {
    val busStopFavorite = BusStopFavorite("53", "As Pereiras")

    viewModel.onBusStopFavoriteClick(busStopFavorite)

    verify(navEventObserver).onChanged(
      NavScreen.Times(BusStopModel("53", "As Pereiras"))
    )
  }

  @Test
  fun `when bus stop is clicked should track the select stop event`() {
    val busStopFavorite = BusStopFavorite("53", "As Pereiras")

    viewModel.onBusStopFavoriteClick(busStopFavorite)

    verify(analyticsTracker).trackEvent(
      AnalyticsEvents.SELECT_STOP,
      mapOf(
        AnalyticsParams.ORIGIN to Screens.FAVORITES,
        AnalyticsParams.STOP_CODE to "53",
        AnalyticsParams.STOP_NAME to "As Pereiras"
      )
    )
  }
}
