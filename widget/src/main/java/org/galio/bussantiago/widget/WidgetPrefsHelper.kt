package org.galio.bussantiago.widget

import android.content.Context
import androidx.core.content.edit

private const val WIDGET_PREFERENCES = "WidgetPrefs"

internal class WidgetPrefsHelper(context: Context) {

  private val prefs = context.getSharedPreferences(WIDGET_PREFERENCES, Context.MODE_PRIVATE)

  fun save(
    code: String? = null,
    name: String? = null,
    hour: String? = null,
    widgetId: Int
  ) {
    prefs.edit(commit = true) {
      code?.let { putString(widgetId.toKeyCode(), it) }
      name?.let { putString(widgetId.toKeyName(), it) }
      hour?.let { putString(widgetId.toKeyHour(), it) }
    }
  }

  fun getCode(widgetId: Int): String {
    return prefs.getString(widgetId.toKeyCode(), "").orEmpty()
  }

  fun getName(widgetId: Int): String {
    return prefs.getString(widgetId.toKeyName(), "").orEmpty()
  }

  fun getHour(widgetId: Int): String {
    return prefs.getString(widgetId.toKeyHour(), "").orEmpty()
  }

  fun remove(widgetId: Int) {
    prefs.edit(commit = true) {
      remove(widgetId.toKeyCode())
      remove(widgetId.toKeyName())
      remove(widgetId.toKeyHour())
    }
  }

  private fun Int.toKeyCode() = this.toString() + "CODE"
  private fun Int.toKeyName() = this.toString() + "NAME"
  private fun Int.toKeyHour() = this.toString() + "HOUR"
}
