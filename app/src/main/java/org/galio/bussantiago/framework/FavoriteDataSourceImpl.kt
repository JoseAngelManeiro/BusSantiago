package org.galio.bussantiago.framework

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.galio.bussantiago.data.local.FavoriteDataSource
import org.galio.bussantiago.domain.model.BusStopFavorite

class FavoriteDataSourceImpl(context: Context) : FavoriteDataSource {

  companion object {
    private const val PREF_FILE_NAME = "bussantiago_app_preferences"
    private const val PREF_KEY_FAVORITE = "pref_key_favorite"
  }

  private val preferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
  private val gson = Gson()
  private val favoritesType = object : TypeToken<ArrayList<BusStopFavorite>>() {}.type

  override fun getAll(): List<BusStopFavorite> {
    val favoritesJson = preferences.getString(PREF_KEY_FAVORITE, null)
    return if (favoritesJson != null) {
      gson.fromJson(favoritesJson, favoritesType)
    } else {
      emptyList()
    }
  }

  override fun remove(busStopFavorite: BusStopFavorite) {
    saveAll(getAll().minus(busStopFavorite))
  }

  override fun save(busStopFavorite: BusStopFavorite) {
    saveAll(getAll().plus(busStopFavorite))
  }

  private fun saveAll(busStopFavorites: List<BusStopFavorite>) {
    val busStopsFavoritesJson = gson.toJson(busStopFavorites, favoritesType)
    preferences.edit().putString(PREF_KEY_FAVORITE, busStopsFavoritesJson).apply()
  }
}
