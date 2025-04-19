package org.galio.bussantiago.data.repository

import org.galio.bussantiago.core.Either
import org.galio.bussantiago.core.Either.Error
import org.galio.bussantiago.core.Either.Success
import org.galio.bussantiago.core.model.Line
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.cache.LineCache
import org.galio.bussantiago.data.mapper.LineMapper

internal class LineRepository(
  private val apiClient: ApiClient,
  private val mapper: LineMapper,
  private val cache: LineCache
) {

  fun getLines(): Either<Exception, List<Line>> {
    val localData = cache.getAll()
    return if (localData.isNotEmpty()) {
      Success(localData)
    } else {
      val serviceResult = apiClient.getLines()
      if (serviceResult.isSuccess) {
        val lines = serviceResult.successValue.map { mapper.toDomain(it) }
        cache.save(lines)
        Success(lines)
      } else {
        Error(serviceResult.errorValue)
      }
    }
  }
}
