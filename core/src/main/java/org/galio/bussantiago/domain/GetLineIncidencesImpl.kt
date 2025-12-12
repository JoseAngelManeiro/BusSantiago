package org.galio.bussantiago.domain

import org.galio.bussantiago.core.GetLineIncidences
import org.galio.bussantiago.core.model.Incidence
import org.galio.bussantiago.data.repository.LineDetailsRepository

internal class GetLineIncidencesImpl(
  private val lineDetailsRepository: LineDetailsRepository
) : GetLineIncidences {

  override fun invoke(lineId: Int): Result<List<Incidence>> {
    return lineDetailsRepository.getLineDetails(lineId).map { it.incidences }
  }
}
