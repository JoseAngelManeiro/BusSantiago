package org.galio.bussantiago.data.repository

import org.galio.bussantiago.core.model.LineDetails
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.cache.LineDetailsCache
import org.galio.bussantiago.data.mapper.LineDetailsMapper

internal class LineDetailsRepository(
  private val apiClient: ApiClient,
  private val mapper: LineDetailsMapper,
  private val cache: LineDetailsCache
) {

  fun getLineDetails(id: Int): Result<LineDetails> {
    val cachedResult = cache.get(id)
    return if (cachedResult.isSuccess) {
      cachedResult
    } else {
      apiClient.getLineDetails(id).map { lineDetailsEntity ->
        mapper.toDomain(lineDetailsEntity).also { lineDetails ->
          cache.save(id, lineDetails)
        }
      }
    }
  }
}
