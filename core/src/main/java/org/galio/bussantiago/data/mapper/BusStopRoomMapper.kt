package org.galio.bussantiago.data.mapper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.galio.bussantiago.core.model.BusStopSearch
import org.galio.bussantiago.core.model.Coordinates
import org.galio.bussantiago.core.model.LineSearch
import org.galio.bussantiago.data.local.room.entity.BusStopEntity

internal class BusStopRoomMapper : Mapper<BusStopEntity, BusStopSearch> {

  private val gson = Gson()

  override fun toDomain(dataModel: BusStopEntity): BusStopSearch {
    val parsedLines: List<LineSearch> = try {
      gson.fromJson(dataModel.linesJson, object : TypeToken<List<LineSearch>>() {}.type) ?: emptyList()
    } catch (e: Exception) {
      emptyList()
    }

    return BusStopSearch(
      id = dataModel.id,
      code = dataModel.code,
      name = dataModel.name,
      zone = dataModel.zone,
      coordinates = Coordinates(dataModel.latitude, dataModel.longitude),
      lines = parsedLines
    )
  }

  fun toEntity(domainModel: BusStopSearch): BusStopEntity {
    return BusStopEntity(
      id = domainModel.id,
      code = domainModel.code,
      name = domainModel.name,
      zone = domainModel.zone,
      latitude = domainModel.coordinates.latitude,
      longitude = domainModel.coordinates.longitude,
      linesJson = gson.toJson(domainModel.lines)
    )
  }
}
