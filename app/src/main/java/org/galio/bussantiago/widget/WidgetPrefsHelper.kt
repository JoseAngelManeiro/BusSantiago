package org.galio.bussantiago.widget

import android.content.Context

private const val WIDGET_PREFERENCES = "WidgetPrefs"

class WidgetPrefsHelper(context: Context) {

  private val prefs = context.getSharedPreferences(
    WIDGET_PREFERENCES,
    Context.MODE_PRIVATE
  )

  fun save(
    code: String? = null,
    name: String? = null,
    hour: String? = null,
    widgetId: Int
  ): Boolean {
    return prefs.edit().apply {
      if (code != null) putString(widgetId.toKeyCode(), code)
      if (name != null) putString(widgetId.toKeyName(), name)
      if (hour != null) putString(widgetId.toKeyHour(), hour)
    }.commit()
  }

  fun getCode(widgetId: Int): String {
    return prefs.getString(widgetId.toKeyCode(), "")!!
  }

  fun getName(widgetId: Int): String {
    return prefs.getString(widgetId.toKeyName(), "")!!
  }

  fun getHour(widgetId: Int): String {
    return prefs.getString(widgetId.toKeyHour(), "")!!
  }

  fun remove(widgetId: Int): Boolean {
    return prefs.edit().apply {
      remove(widgetId.toKeyCode())
      remove(widgetId.toKeyName())
      remove(widgetId.toKeyHour())
    }.commit()
  }

  private fun Int.toKeyCode() = this.toString() + "CODE"
  private fun Int.toKeyName() = this.toString() + "NAME"
  private fun Int.toKeyHour() = this.toString() + "HOUR"
}
