package org.galio.bussantiago.domain

import org.galio.bussantiago.core.GetLines
import org.galio.bussantiago.core.model.Line
import org.galio.bussantiago.data.repository.LineRepository

internal class GetLinesImpl(
  private val lineRepository: LineRepository
) : GetLines {

  override fun invoke(): Result<List<Line>> {
    return lineRepository.getLines()
  }
}
