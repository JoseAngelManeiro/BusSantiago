package org.galio.bussantiago.data.cache

import org.galio.bussantiago.core.model.Line

internal class LineCache(
  private val lines: MutableList<Line> = mutableListOf()
) {

  fun save(lines: List<Line>) {
    this.lines.clear()
    this.lines.addAll(lines)
  }

  fun getAll(): Result<List<Line>> {
    return if (lines.isNotEmpty()) {
      Result.success(lines.toList())
    } else {
      Result.failure(NoSuchElementException())
    }
  }
}
