package org.galio.bussantiago.domain.interactor

import org.galio.bussantiago.Either
import org.galio.bussantiago.domain.model.LineDetails
import org.galio.bussantiago.domain.repository.LineDetailsRepository

class GetLineDetails(
  private val lineDetailsRepository: LineDetailsRepository
) : Interactor<Int, LineDetails> {

  override fun invoke(request: Int): Either<Exception, LineDetails> {
    return lineDetailsRepository.getLineDetails(request)
  }
}
