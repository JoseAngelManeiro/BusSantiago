package org.galio.bussantiago.domain

import org.galio.bussantiago.core.GetLineDetails
import org.galio.bussantiago.core.model.LineDetails
import org.galio.bussantiago.data.repository.LineDetailsRepository

internal class GetLineDetailsImpl(
  private val lineDetailsRepository: LineDetailsRepository
) : GetLineDetails {

  override fun invoke(request: Int): Result<LineDetails> {
    return lineDetailsRepository.getLineDetails(request)
  }
}
