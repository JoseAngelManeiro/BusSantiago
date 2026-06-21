package org.galio.bussantiago.features.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.galio.bussantiago.common.BaseViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.SingleLiveEvent
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.core.SearchAllBusStops
import org.galio.bussantiago.core.model.BusStopSearch
import org.galio.bussantiago.executor.UseCaseExecutor
import org.galio.bussantiago.framework.analytics.AnalyticsEvents
import org.galio.bussantiago.framework.analytics.AnalyticsParams
import org.galio.bussantiago.framework.analytics.AnalyticsTracker
import org.galio.bussantiago.framework.analytics.Screens
import org.galio.bussantiago.navigation.NavScreen

class SearchViewModel(
  private val executor: UseCaseExecutor,
  private val searchAllBusStops: SearchAllBusStops,
  private val analyticsTracker: AnalyticsTracker
) : BaseViewModel(executor) {

  private val _searchEvent = SingleLiveEvent<SearchEvent>()
  private val _busStops = MutableLiveData<Resource<List<BusStopSearch>>>()
  private val _navigationEvent = SingleLiveEvent<NavScreen>()

  val busStops: LiveData<Resource<List<BusStopSearch>>>
    get() = _busStops

  val searchEvent: LiveData<SearchEvent>
    get() = _searchEvent

  val navigationEvent: LiveData<NavScreen>
    get() = _navigationEvent

  fun init() {
    analyticsTracker.trackScreen(Screens.SEARCH)
    loadBusStops()
  }

  fun loadBusStops() {
    _busStops.value = Resource.loading()
    executor(
      useCase = { searchAllBusStops() },
      onSuccess = {
        _busStops.value = Resource.success(it)
      },
      onError = {
        _busStops.value = Resource.error(it)
      }
    )
  }

  fun onMapInfoWindowClicked(busStopModel: BusStopModel) {
    analyticsTracker.trackEvent(
      AnalyticsEvents.SELECT_STOP,
      mapOf(
        AnalyticsParams.ORIGIN to Screens.SEARCH,
        AnalyticsParams.STOP_CODE to busStopModel.code,
        AnalyticsParams.STOP_NAME to busStopModel.name
      )
    )
    _navigationEvent.value = NavScreen.Times(busStopModel)
  }

  fun onSuggestionItemClicked(busStopSearch: BusStopSearch) {
    analyticsTracker.trackEvent(
      AnalyticsEvents.SELECT_SUGGESTION,
      mapOf(
        AnalyticsParams.STOP_CODE to busStopSearch.code,
        AnalyticsParams.STOP_NAME to busStopSearch.name
      )
    )
    _searchEvent.value = SearchEvent.ShowMapInfoWindow(busStopSearch)
  }

  fun onClearTextButtonClicked() {
    _searchEvent.value = SearchEvent.ClearSearchText
  }

  fun onMyLocationButtonClicked() {
    _searchEvent.value = SearchEvent.ShowMapMyLocation
  }

  fun onFavoritesActionButtonClicked() {
    _navigationEvent.value = NavScreen.Favorites
  }

  fun onLinesActionButtonClicked() {
    _navigationEvent.value = NavScreen.Lines
  }

  fun onAboutActionButtonClicked() {
    _navigationEvent.value = NavScreen.About
  }
}
