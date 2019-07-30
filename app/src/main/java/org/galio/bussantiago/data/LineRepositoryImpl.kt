package org.galio.bussantiago.data

import org.galio.bussantiago.common.Either
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.cache.LineCache
import org.galio.bussantiago.data.entity.LineEntity
import org.galio.bussantiago.data.mapper.LineMapper
import org.galio.bussantiago.domain.model.Line
import org.galio.bussantiago.domain.repository.LineRepository

internal class LineRepositoryImpl(
  private val apiClient: ApiClient,
  private val mapper: LineMapper,
  private val cache: LineCache
) : LineRepository {

  override fun getLines(): Either<Exception, List<Line>> {
    return object : PrefetchLocalData<List<LineEntity>, List<Line>>() {
      override fun loadFromLocal() = cache.getAll()

      override fun shouldFetch(data: List<Line>?) = data.isNullOrEmpty()

      override fun loadFromService() = apiClient.getLines()

      override fun saveServiceResult(item: List<LineEntity>) {
        cache.save(item.map { mapper.toDomain(it) })
      }
    }.load()
  }
}
