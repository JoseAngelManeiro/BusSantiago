package org.galio.bussantiago.widget

const val WIDGET_PREFERENCES = "WidgetPrefs"

fun getPreferencesKeyCode(widgetId: Int): String? {
  return widgetId.toString() + "CODE"
}

fun getPreferencesKeyName(widgetId: Int): String? {
  return widgetId.toString() + "NAME"
}

fun getPreferencesKeyHour(widgetId: Int): String? {
  return widgetId.toString() + "HOUR"
}
