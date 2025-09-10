package org.galio.bussantiago.features.favorites

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.core.GetBusStopFavorites
import org.galio.bussantiago.core.model.BusStopFavorite
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
  private val favoritesObserver = mock<Observer<Resource<List<BusStopFavorite>>>>()
  private val navEventObserver = mock<Observer<NavScreen>>()

  private lateinit var viewModel: FavoritesViewModel

  @Before
  fun setUp() {
    viewModel = FavoritesViewModel(executor, getBusStopFavorites)
    viewModel.favoriteModels.observeForever(favoritesObserver)
    viewModel.navigationEvent.observeForever(navEventObserver)
  }

  @Test
  fun `when use case is invoked successfully should load data as expected`() {
    val favorites = listOf(mock<BusStopFavorite>())
    whenever(getBusStopFavorites()).thenSuccess(favorites)

    viewModel.loadFavorites()

    verify(favoritesObserver).onChanged(Resource.success(favorites))
  }

  @Test
  fun `when use case fails should return exception receivedf`() {
    val exception = Exception("Fake exception")
    whenever(getBusStopFavorites()).thenFailure(exception)

    viewModel.loadFavorites()

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
}
