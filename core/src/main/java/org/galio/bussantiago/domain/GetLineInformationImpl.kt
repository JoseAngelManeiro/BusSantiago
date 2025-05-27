package org.galio.bussantiago.domain

import org.galio.bussantiago.core.GetLineInformation
import org.galio.bussantiago.data.repository.LineDetailsRepository

internal class GetLineInformationImpl(
  private val lineDetailsRepository: LineDetailsRepository
) : GetLineInformation {

  override fun invoke(request: Int): Result<String> {
    return lineDetailsRepository.getLineDetails(request).map { it.information }
  }
}
