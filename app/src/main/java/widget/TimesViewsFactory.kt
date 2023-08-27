package widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.DisplayMetrics
import android.view.View
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import org.galio.bussantiago.R
import org.galio.bussantiago.common.model.SynopticModel
import org.galio.bussantiago.features.times.LineRemainingTimeModel
import org.galio.bussantiago.features.times.getDescriptionByMinutes
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TimesViewsFactory(
  private val context: Context,
  intent: Intent
) : RemoteViewsService.RemoteViewsFactory {

  private val widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
    AppWidgetManager.INVALID_APPWIDGET_ID)
  private val widgetPrefsHelper = WidgetPrefsHelper(context)
  private val stopCode = widgetPrefsHelper.getCode(widgetId)

  private lateinit var lineRemainingTimeModels: MutableList<LineRemainingTimeModel>

  override fun onCreate() {
    lineRemainingTimeModels = mutableListOf()
  }

  override fun onDataSetChanged() {
    val appWidgetManager = AppWidgetManager.getInstance(context)

    val remoteViews = RemoteViews(context.packageName, R.layout.app_widget)

    // Init loading mode
    remoteViews.setViewVisibility(R.id.refresh_button, View.GONE)
    remoteViews.setViewVisibility(R.id.progressBar, View.VISIBLE)
    appWidgetManager.updateAppWidget(widgetId, remoteViews)

    // Obtain times from service
    val tempList = mutableListOf<LineRemainingTimeModel>()
    val result = ObtainJson().call(stopCode)
    if (result != null) {
      tempList.addAll(parseToItemWidgets(result))
    }

    // Finish loading mode
    remoteViews.setViewVisibility(R.id.progressBar, View.GONE)
    remoteViews.setViewVisibility(R.id.refresh_button, View.VISIBLE)
    val formatter = SimpleDateFormat("HH:mm", Locale.US)
    val hour = formatter.format(Calendar.getInstance().timeInMillis)
    remoteViews.setTextViewText(R.id.hourSync_textview, hour)
    appWidgetManager.updateAppWidget(widgetId, remoteViews)

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
        val circleSizePx = dpToPixel(40) // synoptic_size = 40dp
        val bitmap = Bitmap.createBitmap(circleSizePx, circleSizePx, Bitmap.Config.ARGB_8888)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val canvas = Canvas(bitmap)
        paint.color = Color.parseColor(lineRemainingTimeModel.synopticModel.style)
        canvas.drawCircle(circleSizePx.toFloat() / 2, circleSizePx.toFloat() / 2,
          (circleSizePx.toFloat() / 2) - 1, paint)

        setImageViewBitmap(R.id.lineWidgetImageView, bitmap)

        setTextViewText(R.id.lineWidgetTextView,
          lineRemainingTimeModel.synopticModel.synoptic.removePrefix("L"))

        setTextViewText(R.id.timeWidgetTextView,
          getDescriptionByMinutes(lineRemainingTimeModel.minutesUntilNextArrival))
      }
    }
  }

  private fun dpToPixel(dp: Int): Int {
    return dp * (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
  }

  private fun parseToItemWidgets(jsonString: String): List<LineRemainingTimeModel> {
    val arrayListTimes = mutableListOf<LineRemainingTimeModel>()

    try {
      val jsonObjectResult = JSONObject(jsonString)
      val jsonArrayLines = jsonObjectResult.getJSONArray("lineas")
      var jsonObjectLine: JSONObject
      var lineRemainingTimeModel: LineRemainingTimeModel
      for (i in 0 until jsonArrayLines.length()) {
        jsonObjectLine = jsonArrayLines[i] as JSONObject
        lineRemainingTimeModel = LineRemainingTimeModel(
          SynopticModel(
            jsonObjectLine.getString("sinoptico"),
            jsonObjectLine.getString("estilo")
          ),
          jsonObjectLine.getInt("minutosProximoPaso")
        )
        arrayListTimes.add(lineRemainingTimeModel)
      }
    } catch (e: JSONException) {
      // If there is an exception, simply return the empty list
    }

    return arrayListTimes
  }

  override fun getCount() = lineRemainingTimeModels.size

  override fun getViewTypeCount() = 1

  override fun onDestroy() { lineRemainingTimeModels.clear() }

  override fun getLoadingView(): RemoteViews? = null

  override fun getItemId(position: Int) = position.toLong()

  override fun hasStableIds() = true
}
