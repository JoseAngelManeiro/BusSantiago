package org.galio.bussantiago.domain.interactor

import org.galio.bussantiago.Either
import org.galio.bussantiago.core.GetLineDetails
import org.galio.bussantiago.data.repository.LineDetailsRepository
import org.galio.bussantiago.domain.model.LineDetails

internal class GetLineDetailsImpl(
  private val lineDetailsRepository: LineDetailsRepository
) : GetLineDetails {

  override fun invoke(request: Int): Either<Exception, LineDetails> {
    return lineDetailsRepository.getLineDetails(request)
  }
}
