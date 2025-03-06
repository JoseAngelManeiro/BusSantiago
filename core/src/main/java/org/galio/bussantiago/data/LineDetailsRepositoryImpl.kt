package org.galio.bussantiago.data

import org.galio.bussantiago.Either
import org.galio.bussantiago.Either.Left
import org.galio.bussantiago.Either.Right
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.cache.LineDetailsCache
import org.galio.bussantiago.data.mapper.LineDetailsMapper
import org.galio.bussantiago.domain.model.LineDetails
import org.galio.bussantiago.domain.repository.LineDetailsRepository

internal class LineDetailsRepositoryImpl(
  private val apiClient: ApiClient,
  private val mapper: LineDetailsMapper,
  private val cache: LineDetailsCache
) : LineDetailsRepository {

  override fun getLineDetails(id: Int): Either<Exception, LineDetails> {
    val localData = cache.get(id)
    return if (localData != null) {
      Right(localData)
    } else {
      val serviceResult = apiClient.getLineDetails(id)
      if (serviceResult.isRight) {
        val lineDetails = mapper.toDomain(serviceResult.rightValue)
        cache.save(id, lineDetails)
        Right(lineDetails)
      } else {
        Left(serviceResult.leftValue)
      }
    }
  }
}
