package org.galio.bussantiago.data.repository

import org.galio.bussantiago.core.model.Line
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.cache.LineCache
import org.galio.bussantiago.data.local.room.LineDao
import org.galio.bussantiago.data.local.room.toEntity
import org.galio.bussantiago.data.mapper.LineMapper

internal class LineRepository(
  private val apiClient: ApiClient,
  private val mapper: LineMapper,
  private val cache: LineCache,
  private val lineDao: LineDao
) {

  fun getLines(): Result<List<Line>> {
    val cachedResult = cache.getAll()
    if (cachedResult.isSuccess) {
      return cachedResult
    }

    val apiResult = apiClient.getLines().map { lineEntities ->
      val lines = lineEntities.map { lineEntity ->
        mapper.toDomain(lineEntity)
      }
      cache.save(lines)
      
      // Save to database, but clear incidences as they change often
      val linesForDb = lines.map { it.copy(incidents = 0).toEntity() }
      try {
        lineDao.clearAndInsertAll(linesForDb)
      } catch (_: Exception) {
        // ignore
      }
      
      lines
    }

    return if (apiResult.isSuccess) {
      apiResult
    } else {
      // Fallback to local DB
      val localLines = lineDao.getAll().map { it.toDomain() }
      if (localLines.isNotEmpty()) {
        Result.success(localLines)
      } else {
        apiResult // return the original network failure
      }
    }
  }
}
