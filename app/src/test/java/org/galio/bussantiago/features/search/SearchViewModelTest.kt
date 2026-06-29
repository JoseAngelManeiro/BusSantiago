package org.galio.bussantiago.features.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.core.SearchAllBusStops
import org.galio.bussantiago.core.model.BusStopSearch
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
import org.mockito.BDDMockito.verify
import org.mockito.kotlin.whenever

class SearchViewModelTest {

  @get:Rule
  var rule: TestRule = InstantTaskExecutorRule()

  private val executor = TestUseCaseExecutor()
  private val searchAllBusStops = mock<SearchAllBusStops>()
  private val analyticsTracker = mock<AnalyticsTracker>()
  private val busStopsObserver = mock<Observer<Resource<List<BusStopSearch>>>>()
  private val searchEventObserver = mock<Observer<SearchEvent>>()
  private val navEventObserver = mock<Observer<NavScreen>>()

  private val searchViewModel = SearchViewModel(executor, searchAllBusStops, analyticsTracker)

  @Before
  fun setUp() {
    searchViewModel.busStops.observeForever(busStopsObserver)
    searchViewModel.searchEvent.observeForever(searchEventObserver)
    searchViewModel.navigationEvent.observeForever(navEventObserver)
  }

  @Test
  fun `load all bus stops successfully should return the data received`() {
    val busStops = listOf<BusStopSearch>(mock())
    whenever(searchAllBusStops()).thenSuccess(busStops)

    searchViewModel.loadBusStops()

    verify(busStopsObserver).onChanged(Resource.loading())
    verify(busStopsObserver).onChanged(Resource.success(busStops))
  }

  @Test
  fun `when load all bus stops fails should return the exception`() {
    val exception = mock<Exception>()
    whenever(searchAllBusStops()).thenFailure(exception)

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

  @Test
  fun `when init is called should track the Search screen`() {
    whenever(searchAllBusStops()).thenSuccess(emptyList())

    searchViewModel.init()

    verify(analyticsTracker).trackScreen(Screens.SEARCH)
  }

  @Test
  fun `when a map info window is clicked should track the select stop event`() {
    val busStopModel = BusStopModel("53", "As Pereiras")

    searchViewModel.onMapInfoWindowClicked(busStopModel)

    verify(analyticsTracker).trackEvent(
      AnalyticsEvents.SELECT_STOP,
      mapOf(
        AnalyticsParams.ORIGIN to Screens.SEARCH,
        AnalyticsParams.STOP_CODE to "53",
        AnalyticsParams.STOP_NAME to "As Pereiras"
      )
    )
  }

  @Test
  fun `when a suggestion is clicked should track the select suggestion event`() {
    val busStopSearch = mock<BusStopSearch>()
    whenever(busStopSearch.code).thenReturn("53")
    whenever(busStopSearch.name).thenReturn("As Pereiras")

    searchViewModel.onSuggestionItemClicked(busStopSearch)

    verify(analyticsTracker).trackEvent(
      AnalyticsEvents.SELECT_SUGGESTION,
      mapOf(
        AnalyticsParams.STOP_CODE to "53",
        AnalyticsParams.STOP_NAME to "As Pereiras"
      )
    )
  }
}
