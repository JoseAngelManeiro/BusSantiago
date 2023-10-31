package org.galio.bussantiago.data

import org.galio.bussantiago.Either
import org.galio.bussantiago.Either.Left
import org.galio.bussantiago.Either.Right
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.cache.LineCache
import org.galio.bussantiago.data.mapper.LineMapper
import org.galio.bussantiago.domain.model.Line
import org.galio.bussantiago.domain.repository.LineRepository

class LineRepositoryImpl(
  private val apiClient: ApiClient,
  private val mapper: LineMapper,
  private val cache: LineCache
) : LineRepository {

  override fun getLines(): Either<Exception, List<Line>> {
    val localData = cache.getAll()
    return if (localData.isNotEmpty()) {
      Right(localData)
    } else {
      val serviceResult = apiClient.getLines()
      if (serviceResult.isRight) {
        val lines = serviceResult.rightValue.map { mapper.toDomain(it) }
        cache.save(lines)
        Right(lines)
      } else {
        Left(serviceResult.leftValue)
      }
    }
  }
}
