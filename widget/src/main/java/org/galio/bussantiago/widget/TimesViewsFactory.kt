package org.galio.bussantiago.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.graphics.createBitmap
import androidx.core.graphics.toColorInt
import org.galio.bussantiago.core.GetBusStopRemainingTimes
import org.galio.bussantiago.shared.LineRemainingTimeModel
import org.galio.bussantiago.shared.TimeFormatter
import org.galio.bussantiago.shared.TimesFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import org.galio.bussantiago.shared.R as sharedR

internal class TimesViewsFactory(
  private val context: Context,
  private val getBusStopRemainingTimes: GetBusStopRemainingTimes,
  intent: Intent
) : RemoteViewsService.RemoteViewsFactory {

  private val widgetIdHelper = WidgetIdHelper()
  private val widgetId = widgetIdHelper.getWidgetId(intent)
  private val widgetPrefsHelper = WidgetPrefsHelper(context)
  private val stopCode = widgetPrefsHelper.getCode(widgetId)

  private val timeFormatter = TimeFormatter()
  private val timesFactory = TimesFactory()

  private lateinit var lineRemainingTimeModels: MutableList<LineRemainingTimeModel>

  override fun onCreate() {
    lineRemainingTimeModels = mutableListOf()
  }

  override fun onDataSetChanged() {
    val appWidgetManager = AppWidgetManager.getInstance(context)

    // Init loading mode
    val loadingViews = RemoteViews(context.packageName, R.layout.app_widget)
    loadingViews.setViewVisibility(R.id.refresh_button, View.GONE)
    loadingViews.setViewVisibility(R.id.progressBar, View.VISIBLE)
    appWidgetManager.updateAppWidget(widgetId, loadingViews)

    // Obtain times from service
    val tempList = mutableListOf<LineRemainingTimeModel>()
    getBusStopRemainingTimes(stopCode).onSuccess { busStopRemainingTimes ->
      tempList.addAll(timesFactory.createLineRemainingTimeModels(busStopRemainingTimes))
    }

    // Finish loading mode
    val resultViews = RemoteViews(context.packageName, R.layout.app_widget)
    resultViews.setViewVisibility(R.id.progressBar, View.GONE)
    resultViews.setViewVisibility(R.id.refresh_button, View.VISIBLE)
    val formatter = SimpleDateFormat("HH:mm", Locale.US)
    val hour = formatter.format(Calendar.getInstance().timeInMillis)
    resultViews.setTextViewText(R.id.hourSync_textview, hour)
    appWidgetManager.updateAppWidget(widgetId, resultViews)

    // Save sync hour in preferences
    widgetPrefsHelper.save(hour = hour, widgetId = widgetId)

    // At the end of the data load, we must rebuild our widget,
    // so that when it is restored it does not suffer loss of instances.
    WidgetProvider.updateWidget(context, appWidgetManager, widgetId)

    // We refresh values from our list, which will be collected from the getViewAt()
    // method to paint the result in the widget.
    lineRemainingTimeModels = tempList
  }

  override fun getViewAt(position: Int): RemoteViews {
    return RemoteViews(context.packageName, R.layout.time_item_widget).apply {
      // Validation that prevents IndexOutOfBoundsException
      lineRemainingTimeModels.getOrNull(position)?.let { lineRemainingTimeModel ->
        val circleSizePx = context.resources.getDimensionPixelSize(sharedR.dimen.synoptic_size)
        val bitmap = createBitmap(circleSizePx, circleSizePx)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val canvas = Canvas(bitmap)
        paint.color = lineRemainingTimeModel.synopticModel.style.toColorInt()
        canvas.drawCircle(
          circleSizePx.toFloat() / 2,
          circleSizePx.toFloat() / 2,
          (circleSizePx.toFloat() / 2) - 1,
          paint
        )

        setImageViewBitmap(R.id.lineWidgetImageView, bitmap)

        setTextViewText(
          R.id.lineWidgetTextView,
          lineRemainingTimeModel.synopticModel.getSynopticFormatted()
        )

        setTextViewText(
          R.id.timeWidgetTextView,
          timeFormatter.getDescription(lineRemainingTimeModel.minutesUntilNextArrival)
        )
      }
    }
  }

  override fun getCount() = lineRemainingTimeModels.size

  override fun getViewTypeCount() = 1

  override fun onDestroy() = lineRemainingTimeModels.clear()

  override fun getLoadingView(): RemoteViews? = null

  override fun getItemId(position: Int) = position.toLong()

  override fun hasStableIds() = true
}
