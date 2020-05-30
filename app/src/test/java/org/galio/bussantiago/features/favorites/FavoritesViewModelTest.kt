package org.galio.bussantiago.features.favorites

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.galio.bussantiago.Either
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.executor.SyncInteractorExecutor
import org.galio.bussantiago.domain.interactor.GetBusStopFavorites
import org.galio.bussantiago.domain.model.BusStopFavorite
import org.galio.bussantiago.util.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

class FavoritesViewModelTest {

  @get:Rule
  var rule: TestRule = InstantTaskExecutorRule()

  private val executor = SyncInteractorExecutor()
  private val getBusStopFavorites = mock<GetBusStopFavorites>()
  private val observer = mock<Observer<Resource<List<BusStopFavorite>>>>()

  private lateinit var viewModel: FavoritesViewModel

  @Before
  fun setUp() {
    viewModel = FavoritesViewModel(executor, getBusStopFavorites)
    viewModel.busStopFavorites.observeForever(observer)
  }

  @Test
  fun `if all goes well, the favorites are loaded correctly`() {
    val request = Unit
    val busStopFavoritesStub = mock<List<BusStopFavorite>>()
    `when`(getBusStopFavorites(request)).thenReturn(Either.Right(busStopFavoritesStub))

    viewModel.loadFavorites()

    verify(observer).onChanged(Resource.loading())
    verify(observer).onChanged(Resource.success(busStopFavoritesStub))
  }

  @Test
  fun `fire the exception received`() {
    val request = Unit
    val exception = Exception("Fake exception")
    `when`(getBusStopFavorites(request)).thenReturn(Either.Left(exception))

    viewModel.loadFavorites()

    verify(observer).onChanged(Resource.loading())
    verify(observer).onChanged(Resource.error(exception))
  }
}
