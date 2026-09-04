package org.galio.bussantiago.data.repository

import org.galio.bussantiago.core.model.LineDetails
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.cache.LineDetailsCache
import org.galio.bussantiago.data.local.room.LineDetailsDao
import org.galio.bussantiago.data.mapper.LineDetailsMapper
import org.galio.bussantiago.data.mapper.LineDetailsRoomMapper

internal class LineDetailsRepository(
  private val apiClient: ApiClient,
  private val mapper: LineDetailsMapper,
  private val roomMapper: LineDetailsRoomMapper,
  private val cache: LineDetailsCache,
  private val lineDetailsDao: LineDetailsDao
) {

  fun getLineDetails(id: Int): Result<LineDetails> {
    val cachedResult = cache.get(id)
    if (cachedResult.isSuccess) {
      return cachedResult
    }

    val apiResult = apiClient.getLineDetails(id).map { lineDetailsEntity ->
      val lineDetails = mapper.toDomain(lineDetailsEntity)
      cache.save(id, lineDetails)

      // Save to database
      try {
        lineDetailsDao.insert(roomMapper.toEntity(lineDetails))
      } catch (_: Exception) {
        // ignore
      }

      lineDetails
    }

    return if (apiResult.isSuccess) {
      apiResult
    } else {
      // Fallback to local DB
      val localLineDetails = lineDetailsDao.get(id)?.let { roomMapper.toDomain(it) }
      if (localLineDetails != null) {
        Result.success(localLineDetails)
      } else {
        apiResult
      }
    }
  }
}
