package org.galio.bussantiago.data

import org.galio.bussantiago.common.Either
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.mapper.LineDetailsMapper
import org.galio.bussantiago.domain.model.LineDetails
import org.galio.bussantiago.domain.repository.LineDetailsRepository

internal class LineDetailsRepositoryImpl(
  private val apiClient: ApiClient,
  private val mapper: LineDetailsMapper
) : LineDetailsRepository {

  override fun getLineDetails(id: Int): Either<Exception, LineDetails> {
    val response = apiClient.getLineDetails(id)
    return if (response.isRight) {
      Either.Right(mapper.toDomain(response.rightValue))
    } else {
      Either.Left(response.leftValue)
    }
  }
}
