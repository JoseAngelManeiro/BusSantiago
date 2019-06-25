package org.galio.bussantiago.data

import org.galio.bussantiago.domain.LineRepository

class LineRepositoryImpl : LineRepository {

  override fun getLines(): List<String> {
    return listOf("Line1", "Line2")
  }
}
