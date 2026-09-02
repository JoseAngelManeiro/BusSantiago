package org.galio.bussantiago.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.galio.bussantiago.core.model.BusStopSearch
import org.galio.bussantiago.core.model.Coordinates
import org.galio.bussantiago.core.model.LineSearch
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "bus_stop")
data class BusStopEntity(
  @PrimaryKey val id: Int,
  val code: String,
  val name: String,
  val zone: String?,
  val latitude: Double,
  val longitude: Double,
  val linesJson: String
) {
  fun toDomain(): BusStopSearch = BusStopSearch(
    id = id,
    code = code,
    name = name,
    zone = zone,
    coordinates = Coordinates(latitude, longitude),
    lines = Gson().fromJson(linesJson, object : TypeToken<List<LineSearch>>() {}.type)
  )
}

fun BusStopSearch.toEntity(): BusStopEntity = BusStopEntity(
  id = id,
  code = code,
  name = name,
  zone = zone,
  latitude = coordinates.latitude,
  longitude = coordinates.longitude,
  linesJson = Gson().toJson(lines)
)
