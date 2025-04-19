package org.galio.bussantiago.domain

import org.galio.bussantiago.core.Either
import org.galio.bussantiago.core.Either.Error
import org.galio.bussantiago.core.Either.Success
import org.galio.bussantiago.core.GetLineInformation
import org.galio.bussantiago.data.repository.LineDetailsRepository

internal class GetLineInformationImpl(
  private val lineDetailsRepository: LineDetailsRepository
) : GetLineInformation {

  override fun invoke(request: Int): Either<Exception, String> {
    val response = lineDetailsRepository.getLineDetails(request)
    return if (response.isSuccess) {
      Success(response.successValue.information)
    } else {
      Error(response.errorValue)
    }
  }
}
