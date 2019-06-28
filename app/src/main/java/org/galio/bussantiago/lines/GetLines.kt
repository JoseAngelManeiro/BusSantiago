package org.galio.bussantiago.lines

import org.galio.bussantiago.common.Either
import org.galio.bussantiago.domain.Interactor
import org.galio.bussantiago.domain.AppExecutors
import org.galio.bussantiago.domain.Line
import org.galio.bussantiago.domain.LineRepository

class GetLines(
  appExecutors: AppExecutors,
  private val lineRepository: LineRepository
) : Interactor<Unit, List<Line>>(appExecutors) {

  override fun execute(request: Unit): Either<Exception, List<Line>> {
    return lineRepository.getLines()
  }
}
