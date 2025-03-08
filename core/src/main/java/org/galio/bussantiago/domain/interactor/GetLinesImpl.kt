package org.galio.bussantiago.domain.interactor

import org.galio.bussantiago.Either
import org.galio.bussantiago.core.GetLines
import org.galio.bussantiago.data.repository.LineRepository
import org.galio.bussantiago.domain.model.Line

internal class GetLinesImpl(private val lineRepository: LineRepository) : GetLines {

  override fun invoke(request: Unit): Either<Exception, List<Line>> {
    return lineRepository.getLines()
  }
}
