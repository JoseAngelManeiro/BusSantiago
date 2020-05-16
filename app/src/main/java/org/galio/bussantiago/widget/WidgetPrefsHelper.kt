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
    val editor = prefs.edit()
    if (code != null) editor.putString(getPreferencesKeyCode(widgetId), code)
    if (name != null) editor.putString(getPreferencesKeyName(widgetId), name)
    if (hour != null) editor.putString(getPreferencesKeyHour(widgetId), name)
    return editor.commit()
  }

  fun getCode(widgetId: Int): String {
    return prefs.getString(getPreferencesKeyCode(widgetId), "")
  }

  fun getName(widgetId: Int): String {
    return prefs.getString(getPreferencesKeyName(widgetId), "")
  }

  fun getHour(widgetId: Int): String {
    return prefs.getString(getPreferencesKeyHour(widgetId), "")
  }

  fun remove(widgetId: Int): Boolean {
    val editor = prefs.edit()
    editor.remove(getPreferencesKeyCode(widgetId))
    editor.remove(getPreferencesKeyName(widgetId))
    editor.remove(getPreferencesKeyHour(widgetId))
    return editor.commit()
  }

  private fun getPreferencesKeyCode(widgetId: Int): String? {
    return widgetId.toString() + "CODE"
  }

  private fun getPreferencesKeyName(widgetId: Int): String? {
    return widgetId.toString() + "NAME"
  }

  private fun getPreferencesKeyHour(widgetId: Int): String? {
    return widgetId.toString() + "HOUR"
  }
}
