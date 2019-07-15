package org.galio.bussantiago.menu

import org.galio.bussantiago.common.Either
import org.galio.bussantiago.domain.Interactor
import org.galio.bussantiago.domain.model.LineDetails
import org.galio.bussantiago.domain.repository.LineDetailsRepository

class GetLineDetails(
  private val lineDetailsRepository: LineDetailsRepository
) : Interactor<Int, LineDetails> {

  override fun invoke(request: Int): Either<Exception, LineDetails> {
    return lineDetailsRepository.getLineDetails(request)
  }
}
