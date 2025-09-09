package org.galio.bussantiago.core

import org.galio.bussantiago.core.model.Line

interface GetLines {
  operator fun invoke(): Result<List<Line>>
}
