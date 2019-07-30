package org.galio.bussantiago.data

import org.galio.bussantiago.common.Either
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.cache.LineDetailsCache
import org.galio.bussantiago.data.entity.LineDetailsEntity
import org.galio.bussantiago.data.mapper.LineDetailsMapper
import org.galio.bussantiago.domain.model.LineDetails
import org.galio.bussantiago.domain.repository.LineDetailsRepository

internal class LineDetailsRepositoryImpl(
  private val apiClient: ApiClient,
  private val mapper: LineDetailsMapper,
  private val cache: LineDetailsCache
) : LineDetailsRepository {

  override fun getLineDetails(id: Int): Either<Exception, LineDetails> {
    return object : PrefetchLocalData<LineDetailsEntity, LineDetails>() {
      override fun loadFromLocal() = cache.get(id)

      override fun shouldFetch(data: LineDetails?) = data == null

      override fun loadFromService() = apiClient.getLineDetails(id)

      override fun saveServiceResult(item: LineDetailsEntity) {
        cache.save(id, mapper.toDomain(item))
      }
    }.load()
  }
}
