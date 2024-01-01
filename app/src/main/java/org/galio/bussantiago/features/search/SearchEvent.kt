package org.galio.bussantiago.features.search

import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.domain.model.BusStopSearch

sealed class SearchEvent {
  data class NavigateToTimes(val busStopModel: BusStopModel) : SearchEvent()
  data class ShowMapInfoWindow(val busStopSearch: BusStopSearch) : SearchEvent()
  object ClearSearchText : SearchEvent()
  object ShowMapMyLocation : SearchEvent()
}
