package org.galio.bussantiago.data.repository

import org.galio.bussantiago.core.Either
import org.galio.bussantiago.core.Either.Error
import org.galio.bussantiago.core.Either.Success
import org.galio.bussantiago.core.model.LineDetails
import org.galio.bussantiago.data.api.ApiClient
import org.galio.bussantiago.data.cache.LineDetailsCache
import org.galio.bussantiago.data.mapper.LineDetailsMapper

internal class LineDetailsRepository(
  private val apiClient: ApiClient,
  private val mapper: LineDetailsMapper,
  private val cache: LineDetailsCache
) {

  fun getLineDetails(id: Int): Either<Exception, LineDetails> {
    val localData = cache.get(id)
    return if (localData != null) {
      Success(localData)
    } else {
      val serviceResult = apiClient.getLineDetails(id)
      if (serviceResult.isSuccess) {
        val lineDetails = mapper.toDomain(serviceResult.successValue)
        cache.save(id, lineDetails)
        Success(lineDetails)
      } else {
        Error(serviceResult.errorValue)
      }
    }
  }
}
