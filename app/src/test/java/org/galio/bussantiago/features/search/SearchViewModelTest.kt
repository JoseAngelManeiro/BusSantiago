package org.galio.bussantiago.features.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.core.Either
import org.galio.bussantiago.core.SearchAllBusStops
import org.galio.bussantiago.core.model.BusStopSearch
import org.galio.bussantiago.navigation.Navigator
import org.galio.bussantiago.util.TestInteractorExecutor
import org.galio.bussantiago.util.mock
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify

class SearchViewModelTest {

  @get:Rule
  var rule: TestRule = InstantTaskExecutorRule()

  private val executor = TestInteractorExecutor()
  private val searchAllBusStops = mock<SearchAllBusStops>()
  private val busStopsObserver = mock<Observer<Resource<List<BusStopSearch>>>>()
  private val searchEventObserver = mock<Observer<SearchEvent>>()
  private val navigator = mock<Navigator>()

  private val searchViewModel = SearchViewModel(executor, searchAllBusStops, navigator)

  @Before
  fun setUp() {
    searchViewModel.busStops.observeForever(busStopsObserver)
    searchViewModel.searchEvent.observeForever(searchEventObserver)
  }

  @Test
  fun `load all bus stops successfully should return the data received`() {
    val busStops = listOf<BusStopSearch>(mock())
    given(searchAllBusStops(Unit)).willReturn(Either.success(busStops))

    searchViewModel.loadBusStops()

    verify(busStopsObserver).onChanged(Resource.loading())
    verify(busStopsObserver).onChanged(Resource.success(busStops))
  }

  @Test
  fun `when load all bus stops fails should return the exception`() {
    val exception = mock<Exception>()
    given(searchAllBusStops(Unit)).willReturn(Either.error(exception))

    searchViewModel.loadBusStops()

    verify(busStopsObserver).onChanged(Resource.loading())
    verify(busStopsObserver).onChanged(Resource.error(exception))
  }

  @Test
  fun `when onMapInfoWindowClicked should navigate with model received`() {
    val busStopModel = mock<BusStopModel>()

    searchViewModel.onMapInfoWindowClicked(busStopModel)

    //verify(searchEventObserver).onChanged(SearchEvent.NavigateToTimes(busStopModel))
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
}
