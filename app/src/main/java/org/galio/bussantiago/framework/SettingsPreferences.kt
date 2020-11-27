package org.galio.bussantiago.framework

import android.content.Context

class SettingsPreferences(
  context: Context
) {

  companion object {
    private const val PREF_FILE_KEY = "org.galio.bussantiago.PREFERENCE_FILE_KEY"
    private const val PREF_HOME_SCREEN_KEY = "pref_home_screen_key"
    const val FAVORITES_HOME_VALUE = 1
    const val LINES_HOME_VALUE = 2
  }

  private val sharedPreferences = context.getSharedPreferences(PREF_FILE_KEY, Context.MODE_PRIVATE)

  fun saveHomeScreenType(value: Int) {
    with(sharedPreferences.edit()) {
      putInt(PREF_HOME_SCREEN_KEY, value)
      apply()
    }
  }

  fun getHomeScreenType(): Int {
    return sharedPreferences.getInt(PREF_HOME_SCREEN_KEY, FAVORITES_HOME_VALUE)
  }
}
