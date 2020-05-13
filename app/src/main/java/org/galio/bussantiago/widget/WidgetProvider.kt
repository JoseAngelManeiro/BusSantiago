package org.galio.bussantiago.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent

class WidgetProvider : AppWidgetProvider() {

  companion object {
    fun updateWidget(context: Context, appWidgetManager: AppWidgetManager, widgetId: Int) {
      println("Widget updateWidget")
    }
  }

  override fun onUpdate(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetIds: IntArray
  ) {
    println("Widget onUpdate")
    appWidgetIds.forEach {
      updateWidget(context, appWidgetManager, it)
    }
    super.onUpdate(context, appWidgetManager, appWidgetIds)
  }

  override fun onReceive(context: Context, intent: Intent) {
    println("Widget onReceive")
    super.onReceive(context, intent)
  }

  override fun onRestored(context: Context?, oldWidgetIds: IntArray?, newWidgetIds: IntArray?) {
    println("Widget onRestored")
    super.onRestored(context, oldWidgetIds, newWidgetIds)
  }
}
