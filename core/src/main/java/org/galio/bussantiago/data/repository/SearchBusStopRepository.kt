package org.galio.bussantiago.data.repository

import org.galio.bussantiago.core.model.BusStopSearch
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.entity.BusStopRequest
import org.galio.bussantiago.data.local.room.BusStopDao
import org.galio.bussantiago.data.local.room.toEntity
import org.galio.bussantiago.data.mapper.BusStopSearchMapper
import android.content.SharedPreferences

internal class SearchBusStopRepository(
  private val apiClient: ApiClient,
  private val mapper: BusStopSearchMapper,
  private val busStopDao: BusStopDao,
  private val sharedPreferences: SharedPreferences
) {

  fun searchAllBusStops(): Result<List<BusStopSearch>> {
    val localStops = busStopDao.getAll()
    
    val lastUpdate = sharedPreferences.getLong(PREF_LAST_UPDATE, 0L)
    val now = System.currentTimeMillis()
    // 3 months in milliseconds (~90 days)
    val threeMonthsInMillis = 90L * 24 * 60 * 60 * 1000
    val needsUpdate = (now - lastUpdate) > threeMonthsInMillis

    if (localStops.isNotEmpty() && !needsUpdate) {
      return Result.success(localStops.map { it.toDomain() })
    }

    // If local is empty or needs update, fetch from API synchronously
    val apiResult = updateFromApi()
    return if (apiResult.isSuccess) {
      Result.success(busStopDao.getAll().map { it.toDomain() })
    } else {
      if (localStops.isNotEmpty()) {
        Result.success(localStops.map { it.toDomain() })
      } else {
        Result.failure(apiResult.exceptionOrNull() ?: Exception("Unknown error"))
      }
    }
  }

  private fun updateFromApi(): Result<Unit> {
    return apiClient.searchBusStop(BusStopRequest("")).map { entities ->
      val domainModels = entities.map { mapper.toDomain(it) }
      busStopDao.clearAndInsertAll(domainModels.map { it.toEntity() })
      sharedPreferences.edit().putLong(PREF_LAST_UPDATE, System.currentTimeMillis()).apply()
    }
  }

  companion object {
    private const val PREF_LAST_UPDATE = "pref_last_bus_stop_search_update"
  }
}
