package org.galio.bussantiago.domain

import org.galio.bussantiago.core.Either
import org.galio.bussantiago.core.Either.Error
import org.galio.bussantiago.core.Either.Success
import org.galio.bussantiago.core.GetLineIncidences
import org.galio.bussantiago.core.model.Incidence
import org.galio.bussantiago.data.repository.LineDetailsRepository

internal class GetLineIncidencesImpl(
  private val lineDetailsRepository: LineDetailsRepository
) : GetLineIncidences {

  override fun invoke(request: Int): Either<Exception, List<Incidence>> {
    val response = lineDetailsRepository.getLineDetails(request)
    return if (response.isSuccess) {
      Success(response.successValue.incidences)
    } else {
      Error(response.errorValue)
    }
  }
}
