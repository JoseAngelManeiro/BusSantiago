package org.galio.bussantiago.features.incidences

import org.galio.bussantiago.common.Either
import org.galio.bussantiago.common.Either.Right
import org.galio.bussantiago.common.Either.Left
import org.galio.bussantiago.domain.Interactor
import org.galio.bussantiago.domain.model.Incidence
import org.galio.bussantiago.domain.repository.LineDetailsRepository

class GetLineIncidences(
  private val lineDetailsRepository: LineDetailsRepository
) : Interactor<Int, List<Incidence>> {

  override fun invoke(request: Int): Either<Exception, List<Incidence>> {
    val response = lineDetailsRepository.getLineDetails(request)
    return if (response.isRight) {
      Right(response.rightValue.incidences)
    } else {
      Left(response.leftValue)
    }
  }
}
