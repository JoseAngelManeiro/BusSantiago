package org.galio.bussantiago.lines

import org.galio.bussantiago.domain.LineRepository

class GetLines(private val lineRepository: LineRepository) {

  operator fun invoke(): List<String> {
    return lineRepository.getLines()
  }
}
