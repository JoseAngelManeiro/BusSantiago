package org.galio.bussantiago.features.favorites

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.core.Either
import org.galio.bussantiago.core.GetBusStopFavorites
import org.galio.bussantiago.core.model.BusStopFavorite
import org.galio.bussantiago.util.TestInteractorExecutor
import org.galio.bussantiago.util.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever

class FavoritesViewModelTest {

  @get:Rule
  var rule: TestRule = InstantTaskExecutorRule()

  private val executor = TestInteractorExecutor()
  private val getBusStopFavorites = mock<GetBusStopFavorites>()
  private val observer = mock<Observer<Resource<List<BusStopFavorite>>>>()

  private lateinit var viewModel: FavoritesViewModel

  @Before
  fun setUp() {
    viewModel = FavoritesViewModel(executor, getBusStopFavorites)
    viewModel.favoriteModels.observeForever(observer)
  }

  @Test
  fun `when use case is invoked successfully should load data as expected`() {
    val favorites = listOf(mock<BusStopFavorite>())
    whenever(getBusStopFavorites(Unit)).thenReturn(Either.Success(favorites))

    viewModel.loadFavorites()

    verify(observer).onChanged(Resource.success(favorites))
  }

  @Test
  fun `when use case fails should return exception receivedf`() {
    val exception = Exception("Fake exception")
    whenever(getBusStopFavorites(Unit)).thenReturn(Either.Error(exception))

    viewModel.loadFavorites()

    verify(observer).onChanged(Resource.error(exception))
  }
}
