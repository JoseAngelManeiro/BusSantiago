package org.galio.bussantiago.data.api

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.galio.bussantiago.data.entity.BusStopRequest
import org.galio.bussantiago.data.entity.BusStopRemainingTimesEntity
import org.galio.bussantiago.data.entity.BusStopSearchEntity
import org.galio.bussantiago.data.entity.LineDetailsEntity
import org.galio.bussantiago.data.entity.LineEntity
import java.io.InputStreamReader

internal class ApiClient(
  private val context: Context,
  baseEndpoint: String = ""
) {

  private val gson = Gson()

  fun getLines(): Result<List<LineEntity>> = readJsonAsset("lines.json")

  fun getLineDetails(id: Int): Result<LineDetailsEntity> = readJsonAsset("line_details.json")

  fun getBusStopRemainingTimes(code: String): Result<BusStopRemainingTimesEntity> =
    readJsonAsset("remaining_times.json")

  fun searchBusStop(request: BusStopRequest): Result<List<BusStopSearchEntity>> =
    readJsonAsset("bus_stop_search.json")

  private inline fun <reified T> readJsonAsset(fileName: String): Result<T> {
    return try {
      val inputStream = context.assets.open("mock/$fileName")
      val reader = InputStreamReader(inputStream)
      val type = object : TypeToken<T>() {}.type
      val data: T = gson.fromJson(reader, type)
      Result.success(data)
    } catch (e: Exception) {
      Result.failure(e)
    }
  }
}
