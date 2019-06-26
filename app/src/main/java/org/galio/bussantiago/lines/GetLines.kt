package org.galio.bussantiago.lines

import org.galio.bussantiago.common.Either
import org.galio.bussantiago.common.Interactor
import org.galio.bussantiago.domain.LineRepository

class GetLines(
  private val lineRepository: LineRepository
) : Interactor<Unit, List<String>>() {

  override fun execute(request: Unit): Either<Exception, List<String>> {
    return lineRepository.getLines()
  }
}
