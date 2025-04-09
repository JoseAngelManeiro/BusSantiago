package org.galio.bussantiago.widget

import android.appwidget.AppWidgetManager
import android.content.Intent

internal class WidgetIdHelper {

  fun getWidgetId(intent: Intent): Int {
    return intent.getIntExtra(
      AppWidgetManager.EXTRA_APPWIDGET_ID,
      AppWidgetManager.INVALID_APPWIDGET_ID
    )
  }

  fun isWidgetIdValid(widgetId: Int) = widgetId != AppWidgetManager.INVALID_APPWIDGET_ID
}
