package org.galio.bussantiago.domain.interactor

import org.galio.bussantiago.Either
import org.galio.bussantiago.Either.Left
import org.galio.bussantiago.Either.Right
import org.galio.bussantiago.core.GetLineIncidences
import org.galio.bussantiago.domain.model.Incidence
import org.galio.bussantiago.domain.repository.LineDetailsRepository

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
