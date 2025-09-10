package org.galio.bussantiago.domain

import org.galio.bussantiago.core.GetLineInformation
import org.galio.bussantiago.data.repository.LineDetailsRepository

internal class GetLineInformationImpl(
  private val lineDetailsRepository: LineDetailsRepository
) : GetLineInformation {

  override fun invoke(lineId: Int): Result<String> {
    return lineDetailsRepository.getLineDetails(lineId).map { it.information }
  }
}
