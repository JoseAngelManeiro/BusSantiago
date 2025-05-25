package org.galio.bussantiago.features.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.core.SearchAllBusStops
import org.galio.bussantiago.core.model.BusStopSearch
import org.galio.bussantiago.navigation.NavScreen
import org.galio.bussantiago.util.TestInteractorExecutor
import org.galio.bussantiago.util.mock
import org.galio.bussantiago.util.thenFailure
import org.galio.bussantiago.util.thenSuccess
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.BDDMockito.verify
import org.mockito.kotlin.whenever

class SearchViewModelTest {

  @get:Rule
  var rule: TestRule = InstantTaskExecutorRule()

  private val executor = TestInteractorExecutor()
  private val searchAllBusStops = mock<SearchAllBusStops>()
  private val busStopsObserver = mock<Observer<Resource<List<BusStopSearch>>>>()
  private val searchEventObserver = mock<Observer<SearchEvent>>()
  private val navEventObserver = mock<Observer<NavScreen>>()

  private val searchViewModel = SearchViewModel(executor, searchAllBusStops)

  @Before
  fun setUp() {
    searchViewModel.busStops.observeForever(busStopsObserver)
    searchViewModel.searchEvent.observeForever(searchEventObserver)
    searchViewModel.navigationEvent.observeForever(navEventObserver)
  }

  @Test
  fun `load all bus stops successfully should return the data received`() {
    val busStops = listOf<BusStopSearch>(mock())
    whenever(searchAllBusStops(Unit)).thenSuccess(busStops)

    searchViewModel.loadBusStops()

    verify(busStopsObserver).onChanged(Resource.loading())
    verify(busStopsObserver).onChanged(Resource.success(busStops))
  }

  @Test
  fun `when load all bus stops fails should return the exception`() {
    val exception = mock<Exception>()
    whenever(searchAllBusStops(Unit)).thenFailure(exception)

    searchViewModel.loadBusStops()

    verify(busStopsObserver).onChanged(Resource.loading())
    verify(busStopsObserver).onChanged(Resource.error(exception))
  }

  @Test
  fun `when onMapInfoWindowClicked should navigate with model received`() {
    val busStopModel = mock<BusStopModel>()

    searchViewModel.onMapInfoWindowClicked(busStopModel)

    verify(navEventObserver).onChanged(NavScreen.Times(busStopModel))
  }

  @Test
  fun `when onSuggestionItemClicked should show info window with model received`() {
    val busStopSearch = mock<BusStopSearch>()

    searchViewModel.onSuggestionItemClicked(busStopSearch)

    verify(searchEventObserver).onChanged(SearchEvent.ShowMapInfoWindow(busStopSearch))
  }

  @Test
  fun `when onClearTextButtonClicked should clear the search text`() {
    searchViewModel.onClearTextButtonClicked()

    verify(searchEventObserver).onChanged(SearchEvent.ClearSearchText)
  }

  @Test
  fun `when onMyLocationButtonClicked should show my location`() {
    searchViewModel.onMyLocationButtonClicked()

    verify(searchEventObserver).onChanged(SearchEvent.ShowMapMyLocation)
  }

  @Test
  fun `when onFavoritesActionButtonClicked should navigate to the expected screen`() {
    searchViewModel.onFavoritesActionButtonClicked()

    verify(navEventObserver).onChanged(NavScreen.Favorites)
  }

  @Test
  fun `when onLinesActionButtonClicked should navigate to the expected screen`() {
    searchViewModel.onLinesActionButtonClicked()

    verify(navEventObserver).onChanged(NavScreen.Lines)
  }

  @Test
  fun `when onAboutActionButtonClicked should navigate to the expected screen`() {
    searchViewModel.onAboutActionButtonClicked()

    verify(navEventObserver).onChanged(NavScreen.About)
  }
}
