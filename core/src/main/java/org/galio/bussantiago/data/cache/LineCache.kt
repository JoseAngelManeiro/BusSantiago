package org.galio.bussantiago.data.cache

import org.galio.bussantiago.domain.model.Line

class LineCache(private val lines: MutableList<Line> = mutableListOf()) {

  fun save(lines: List<Line>) {
    this.lines.clear()
    this.lines.addAll(lines)
  }

  fun getAll(): List<Line> = lines.toList()
}
