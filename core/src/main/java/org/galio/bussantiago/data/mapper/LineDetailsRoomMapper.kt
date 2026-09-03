package org.galio.bussantiago.data.mapper

import org.galio.bussantiago.core.model.LineDetails
import org.galio.bussantiago.core.model.Route
import org.galio.bussantiago.data.local.room.LineDetailsEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

internal class LineDetailsRoomMapper : Mapper<LineDetailsEntity, LineDetails> {

  private val gson = Gson()

  override fun toDomain(dataModel: LineDetailsEntity): LineDetails {
    val parsedRoutes: List<Route> = try {
      gson.fromJson(dataModel.routesJson, object : TypeToken<List<Route>>() {}.type) ?: emptyList()
    } catch (e: Exception) {
      emptyList()
    }

    return LineDetails(
      id = dataModel.id,
      code = dataModel.code,
      synoptic = dataModel.synoptic,
      name = dataModel.name,
      information = dataModel.information,
      style = dataModel.style,
      routes = parsedRoutes,
      incidences = emptyList() // Incidences are not saved in local DB
    )
  }

  fun toEntity(domainModel: LineDetails): LineDetailsEntity {
    return LineDetailsEntity(
      id = domainModel.id,
      code = domainModel.code,
      synoptic = domainModel.synoptic,
      name = domainModel.name,
      information = domainModel.information,
      style = domainModel.style,
      routesJson = gson.toJson(domainModel.routes)
    )
  }
}
