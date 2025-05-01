package org.galio.bussantiago.features.search

import org.galio.bussantiago.core.model.BusStopSearch

sealed class SearchEvent {
  data class ShowMapInfoWindow(val busStopSearch: BusStopSearch) : SearchEvent()
  data object ClearSearchText : SearchEvent()
  data object ShowMapMyLocation : SearchEvent()
}
