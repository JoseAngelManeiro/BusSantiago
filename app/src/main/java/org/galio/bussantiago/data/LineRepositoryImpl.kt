package org.galio.bussantiago.data

import org.galio.bussantiago.common.Either
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.mapper.LineMapper
import org.galio.bussantiago.domain.model.Line
import org.galio.bussantiago.domain.repository.LineRepository

internal class LineRepositoryImpl(
  private val apiClient: ApiClient,
  private val mapper: LineMapper
) : LineRepository {

  override fun getLines(): Either<Exception, List<Line>> {
    val response = apiClient.getLines()
    return if (response.isRight) {
      Either.Right(response.rightValue.map { mapper.toDomain(it) })
    } else {
      Either.Left(response.leftValue)
    }
  }
}
