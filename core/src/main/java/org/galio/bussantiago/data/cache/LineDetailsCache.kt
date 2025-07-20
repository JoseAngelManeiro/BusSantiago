package org.galio.bussantiago.data.cache

import org.galio.bussantiago.core.model.LineDetails

internal class LineDetailsCache(
  private val linesDetails: MutableMap<Int, LineDetails> = mutableMapOf()
) {

  fun save(key: Int, lineDetails: LineDetails) {
    linesDetails[key] = lineDetails
  }

  fun get(key: Int): Result<LineDetails> {
    return linesDetails[key]?.let {
      Result.success(it)
    } ?: Result.failure(NoSuchElementException())
  }
}
