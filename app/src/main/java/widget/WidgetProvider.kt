package widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import androidx.navigation.NavDeepLinkBuilder
import org.galio.bussantiago.R
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.features.times.TimesFragment

class WidgetProvider : AppWidgetProvider() {

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
        data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))
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
        data = Uri.parse(toUri(Intent.URI_INTENT_SCHEME))
      }
      val refreshPIntent = PendingIntent.getBroadcast(
        context, 0,
        refreshIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
      )
      remoteViews.setOnClickPendingIntent(R.id.syncView, refreshPIntent)

      // Declare an explicit deep link to launch TimesFragment
      val navPIntent = NavDeepLinkBuilder(context)
        .setGraph(R.navigation.nav_graph)
        .setDestination(R.id.timesFragment)
        .setArguments(TimesFragment.createArguments(BusStopModel(code, name)))
        .createPendingIntent()
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
      val widgetId = intent.getIntExtra(
        AppWidgetManager.EXTRA_APPWIDGET_ID,
        AppWidgetManager.INVALID_APPWIDGET_ID
      )
      if (widgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
        appWidgetManager.notifyAppWidgetViewDataChanged(widgetId, R.id.times_listview)
      }
    }
    super.onReceive(context, intent)
  }

  override fun onDeleted(context: Context, appWidgetIds: IntArray) {
    val prefs = WidgetPrefsHelper(context)
    appWidgetIds.forEach { widgetId ->
      if (widgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
        prefs.remove(widgetId)
      }
    }
    super.onDeleted(context, appWidgetIds)
  }
}
