package org.galio.bussantiago.data.repository

import org.galio.bussantiago.core.model.Line
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.cache.LineCache
import org.galio.bussantiago.data.mapper.LineMapper

internal class LineRepository(
  private val apiClient: ApiClient,
  private val mapper: LineMapper,
  private val cache: LineCache
) {

  fun getLines(): Result<List<Line>> {
    val cachedResult = cache.getAll()
    return if (cachedResult.isSuccess) {
      cachedResult
    } else {
      apiClient.getLines().map { lineEntities ->
        lineEntities.map { lineEntity ->
          mapper.toDomain(lineEntity)
        }.also { lines ->
          cache.save(lines)
        }
      }
    }
  }
}
