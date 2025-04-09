package org.galio.bussantiago.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.net.toUri
import org.galio.bussantiago.shared.DeeplinkHelper

internal class WidgetProvider : AppWidgetProvider() {

  companion object {
    fun updateWidget(context: Context, appWidgetManager: AppWidgetManager, widgetId: Int) {
      val prefs = WidgetPrefsHelper(context)
      val code = prefs.getCode(widgetId)
      val name = prefs.getName(widgetId)
      val hour = prefs.getHour(widgetId)

      val remoteViews = RemoteViews(context.packageName, R.layout.app_widget)

      // Connect the service that will load the times with our listView
      val widgetServiceIntent = Intent(context, WidgetService::class.java).apply {
        putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
        data = toUri(Intent.URI_INTENT_SCHEME).toUri()
      }
      remoteViews.setRemoteAdapter(R.id.times_listview, widgetServiceIntent)
      // Define the view to be shown when there are no items
      remoteViews.setEmptyView(R.id.times_listview, R.id.empty_message_textview)

      // Paint header values
      remoteViews.setTextViewText(R.id.codeStop_textview, code)
      remoteViews.setTextViewText(R.id.nameStop_textview, name)
      remoteViews.setTextViewText(R.id.hourSync_textview, hour)

      // Declare the Intent to manage the manual data refresh
      val refreshIntent = Intent(context, WidgetProvider::class.java).apply {
        action = context.getString(R.string.action_refresh_widget)
        putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
        data = toUri(Intent.URI_INTENT_SCHEME).toUri()
      }
      val refreshPIntent = PendingIntent.getBroadcast(
        context,
        0,
        refreshIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
      )
      remoteViews.setOnClickPendingIntent(R.id.syncView, refreshPIntent)

      val deeplink = DeeplinkHelper.getTimesDeeplink(context, code, name)
      val intent = Intent(Intent.ACTION_VIEW, deeplink.toUri()).apply {
        `package` = context.packageName // Ensure it opens in this app
      }
      val navPIntent = PendingIntent.getActivity(
        context,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
      )
      remoteViews.setOnClickPendingIntent(R.id.codeStop_textview, navPIntent)

      // Instruct the widget manager to update the widget
      appWidgetManager.updateAppWidget(widgetId, remoteViews)
    }
  }

  override fun onUpdate(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetIds: IntArray
  ) {
    appWidgetIds.forEach {
      updateWidget(context, appWidgetManager, it)
    }
    super.onUpdate(context, appWidgetManager, appWidgetIds)
  }

  override fun onReceive(context: Context, intent: Intent) {
    val appWidgetManager = AppWidgetManager.getInstance(context)
    if (intent.action == context.getString(R.string.action_refresh_widget)) {
      val widgetIdHelper = WidgetIdHelper()
      val widgetId = widgetIdHelper.getWidgetId(intent)
      if (widgetIdHelper.isWidgetIdValid(widgetId)) {
        appWidgetManager.notifyAppWidgetViewDataChanged(widgetId, R.id.times_listview)
      }
    }
    super.onReceive(context, intent)
  }

  override fun onDeleted(context: Context, appWidgetIds: IntArray) {
    val prefs = WidgetPrefsHelper(context)
    val widgetIdHelper = WidgetIdHelper()
    appWidgetIds.forEach { widgetId ->
      if (widgetIdHelper.isWidgetIdValid(widgetId)) {
        prefs.remove(widgetId)
      }
    }
    super.onDeleted(context, appWidgetIds)
  }
}
