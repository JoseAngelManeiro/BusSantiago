package org.galio.bussantiago.framework.analytics

interface AnalyticsTracker {
  fun trackScreen(screenName: String)
  fun trackEvent(eventName: String, params: Map<String, Any?> = emptyMap())
}

object Screens {
  const val LINES = "Lines"
  const val MENU = "LineMenu"
  const val TIMES = "RemainingTimes"
  const val SEARCH = "SearchMap"
  const val LINE_STOPS_MAP = "LineStopsMap"
  const val LINE_STOPS_LIST = "LineStopsList"
  const val FAVORITES = "Favorites"
  const val ABOUT = "About"
  const val INCIDENCES = "Incidences"
  const val INFORMATION = "Information"
}

object AnalyticsEvents {
  const val SELECT_SUGGESTION = "select_suggestion"
  const val SELECT_STOP = "select_stop"
  const val SELECT_LINE = "select_line"
  const val SELECT_LINE_ROUTE = "select_line_route"
  const val TOGGLE_FAVORITE = "toggle_favorite"
}

object AnalyticsParams {
  const val STOP_CODE = "stop_code"
  const val STOP_NAME = "stop_name"
  const val LINE_ID = "line_id"
  const val MENU_OPTION = "menu_option"
  const val IS_FAVORITE = "is_favorite"
  const val ORIGIN = "origin"
}
