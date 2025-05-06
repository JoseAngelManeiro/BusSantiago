package org.galio.bussantiago.features.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.galio.bussantiago.common.BaseViewModel
import org.galio.bussantiago.common.Resource
import org.galio.bussantiago.common.SingleLiveEvent
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.core.SearchAllBusStops
import org.galio.bussantiago.core.model.BusStopSearch
import org.galio.bussantiago.executor.InteractorExecutor
import org.galio.bussantiago.navigation.NavScreen
import org.galio.bussantiago.navigation.Navigator

class SearchViewModel(
  private val executor: InteractorExecutor,
  private val searchAllBusStops: SearchAllBusStops,
  private val navigator: Navigator
) : BaseViewModel(executor) {

  private val _searchEvent = SingleLiveEvent<SearchEvent>()
  private val _busStops = MutableLiveData<Resource<List<BusStopSearch>>>()

  val busStops: LiveData<Resource<List<BusStopSearch>>>
    get() = _busStops

  val searchEvent: LiveData<SearchEvent>
    get() = _searchEvent

  fun loadBusStops() {
    _busStops.value = Resource.loading()
    executor(
      interactor = searchAllBusStops,
      request = Unit,
      onSuccess = {
        _busStops.value = Resource.success(it)
      },
      onError = {
        _busStops.value = Resource.error(it)
      }
    )
  }

  fun onMapInfoWindowClicked(busStopModel: BusStopModel) {
    navigator.navigate(NavScreen.Times(busStopModel))
  }

  fun onSuggestionItemClicked(busStopSearch: BusStopSearch) {
    _searchEvent.value = SearchEvent.ShowMapInfoWindow(busStopSearch)
  }

  fun onClearTextButtonClicked() {
    _searchEvent.value = SearchEvent.ClearSearchText
  }

  fun onMyLocationButtonClicked() {
    _searchEvent.value = SearchEvent.ShowMapMyLocation
  }

  fun onFavoritesActionButtonClicked() {
    navigator.navigate(NavScreen.Favorites)
  }

  fun onLinesActionButtonClicked() {
    navigator.navigate(NavScreen.Lines)
  }

  fun onAboutActionButtonClicked() {
    navigator.navigate(NavScreen.About)
  }
}
