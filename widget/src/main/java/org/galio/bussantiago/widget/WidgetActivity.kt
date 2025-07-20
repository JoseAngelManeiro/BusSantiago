package org.galio.bussantiago.widget

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import org.galio.bussantiago.core.GetBusStopFavorites
import org.galio.bussantiago.core.model.BusStopFavorite
import org.galio.bussantiago.shared.BusStopFavoritesAdapter
import org.galio.bussantiago.widget.databinding.WidgetActivityBinding
import org.koin.android.ext.android.inject

internal class WidgetActivity : AppCompatActivity() {

  private lateinit var binding: WidgetActivityBinding
  private val getBusStopFavorites: GetBusStopFavorites by inject()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = WidgetActivityBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)

    // First we set a default value
    setResult(RESULT_CANCELED)

    intent.extras?.let {
      // Get the ID of the widget that is being configured
      val widgetIdHelper = WidgetIdHelper()
      val widgetId = widgetIdHelper.getWidgetId(intent)
      if (widgetIdHelper.isWidgetIdValid(widgetId)) {
        getBusStopFavorites(Unit).fold(
          onSuccess = { busStopFavorites ->
            if (busStopFavorites.isEmpty()) {
              binding.noFavoritesTextView.visibility = View.VISIBLE
            } else {
              binding.favoritesRecyclerView.adapter =
                BusStopFavoritesAdapter(busStopFavorites) {
                  onBusStopFavoriteClick(it, widgetId)
                }
            }
          },
          onFailure = {
            // When the use case fails we show nothing
            binding.noFavoritesTextView.visibility = View.VISIBLE
          }
        )
      } else {
        Log.w("WidgetActivity", "Invalid app widget id.")
      }
    } ?: Log.w("WidgetActivity", "Intent extra params are null.")
  }

  private fun onBusStopFavoriteClick(busStopFavorite: BusStopFavorite, widgetId: Int) {
    val widgetPrefsHelper = WidgetPrefsHelper(this)
    // Save stop code and name in preferences linked to widget id
    widgetPrefsHelper.save(
      code = busStopFavorite.code,
      name = busStopFavorite.name,
      widgetId = widgetId
    )

    // Invoke updateWidget to repaint the widget view
    val appWidgetManager = AppWidgetManager.getInstance(this)
    WidgetProvider.updateWidget(this, appWidgetManager, widgetId)

    val intent = Intent()
    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
    setResult(RESULT_OK, intent)
    finish()
  }
}
