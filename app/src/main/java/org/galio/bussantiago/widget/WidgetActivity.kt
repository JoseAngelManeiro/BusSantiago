package org.galio.bussantiago.widget

import android.appwidget.AppWidgetManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.widget_activity.*
import org.galio.bussantiago.R
import org.galio.bussantiago.data.local.FavoriteDataSource
import org.galio.bussantiago.domain.model.BusStopFavorite
import org.galio.bussantiago.features.favorites.BusStopFavoritesAdapter
import org.koin.android.ext.android.inject

class WidgetActivity : AppCompatActivity() {

  private val favoriteDataSource: FavoriteDataSource by inject()
  private val widgetPrefsHelper: WidgetPrefsHelper by inject()

  private var widgetId: Int = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.widget_activity)

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
        val busStopFavorites = favoriteDataSource.getAll()
        if (busStopFavorites.isEmpty()) {
          noFavoritesTextView.visibility = View.VISIBLE
        } else {
          favoritesRecyclerView.adapter =
            BusStopFavoritesAdapter(busStopFavorites) { onBusStopFavoriteClick(it) }
        }
      } else {
        throw IllegalArgumentException(this::class.java.simpleName + ": Invalid App Widget ID")
      }
    } else {
      throw NullPointerException(this::class.java.simpleName + ": Null Intent params")
    }
  }

  private fun onBusStopFavoriteClick(busStopFavorite: BusStopFavorite) {
    // Save stop code and name in preferences linked to widget id
    widgetPrefsHelper.save(
      code = busStopFavorite.code,
      name = busStopFavorite.name,
      widgetId = widgetId
    )

    // Invoke updateWidget to repaint the widget view
    val appWidgetManager = AppWidgetManager.getInstance(this)
    WidgetProvider.updateWidget(this, appWidgetManager, widgetId)

    setResult(RESULT_OK)
    finish()
  }
}
