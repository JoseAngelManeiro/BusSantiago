package org.galio.bussantiago.data

import org.galio.bussantiago.common.Either
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.domain.LineRepository

internal class LineRepositoryImpl(
  private val apiClient: ApiClient
) : LineRepository {

  override fun getLines(): Either<Exception, List<String>> {
    val response = apiClient.getLines()
    return if (response.isRight) {
      Either.Right(listOf(response.rightValue.toString()))
    } else {
      Either.Left(response.leftValue)
    }
  }
}
