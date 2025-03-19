package org.galio.bussantiago.domain

import org.galio.bussantiago.core.Either
import org.galio.bussantiago.core.Either.Left
import org.galio.bussantiago.core.Either.Right
import org.galio.bussantiago.core.GetLineIncidences
import org.galio.bussantiago.core.model.Incidence
import org.galio.bussantiago.data.repository.LineDetailsRepository

internal class GetLineIncidencesImpl(
  private val lineDetailsRepository: LineDetailsRepository
) : GetLineIncidences {

  override fun invoke(request: Int): Either<Exception, List<Incidence>> {
    val response = lineDetailsRepository.getLineDetails(request)
    return if (response.isRight) {
      Right(response.rightValue.incidences)
    } else {
      Left(response.leftValue)
    }
  }
}
