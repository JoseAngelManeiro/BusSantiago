package org.galio.bussantiago.domain.interactor

import org.galio.bussantiago.Either
import org.galio.bussantiago.domain.model.Line
import org.galio.bussantiago.domain.repository.LineRepository

class GetLines(
  private val lineRepository: LineRepository
) : Interactor<Unit, List<Line>> {

  override fun invoke(request: Unit): Either<Exception, List<Line>> {
    return lineRepository.getLines()
  }
}
