package org.galio.bussantiago.widget

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import org.galio.bussantiago.core.GetBusStopFavorites
import org.galio.bussantiago.core.model.BusStopFavorite
import org.galio.bussantiago.shared.BusStopFavoritesAdapter
import org.galio.bussantiago.widget.databinding.WidgetActivityBinding
import org.koin.android.ext.android.inject

class WidgetActivity : AppCompatActivity() {

  private lateinit var binding: WidgetActivityBinding

  private val getBusStopFavorites: GetBusStopFavorites by inject()

  private var widgetId: Int = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = WidgetActivityBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)

    // First we set a default value
    setResult(RESULT_CANCELED)

    val params = intent.extras
    if (params != null) {
      // Get the ID of the widget that is being configured
      widgetId = params.getInt(
        AppWidgetManager.EXTRA_APPWIDGET_ID,
        AppWidgetManager.INVALID_APPWIDGET_ID
      )
      if (widgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
        getBusStopFavorites(Unit).fold(
          leftOp = {
            // When the use case fails we show nothing
            binding.noFavoritesTextView.visibility = View.VISIBLE
          },
          rightOp = { busStopFavorites ->
            if (busStopFavorites.isEmpty()) {
              binding.noFavoritesTextView.visibility = View.VISIBLE
            } else {
              binding.favoritesRecyclerView.adapter =
                BusStopFavoritesAdapter(busStopFavorites) { onBusStopFavoriteClick(it) }
            }
          }
        )
      } else {
        throw IllegalArgumentException(this::class.java.simpleName + ": Invalid App Widget ID")
      }
    } else {
      throw NullPointerException(this::class.java.simpleName + ": Null Intent params")
    }
  }

  private fun onBusStopFavoriteClick(busStopFavorite: BusStopFavorite) {
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
