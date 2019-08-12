package org.galio.bussantiago.features.lines

import org.galio.bussantiago.common.Either
import org.galio.bussantiago.domain.Interactor
import org.galio.bussantiago.domain.model.Line
import org.galio.bussantiago.domain.repository.LineRepository

class GetLines(
  private val lineRepository: LineRepository
) : Interactor<Unit, List<Line>> {

  override fun invoke(request: Unit): Either<Exception, List<Line>> {
    return lineRepository.getLines()
  }
}
