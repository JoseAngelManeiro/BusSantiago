package org.galio.bussantiago.data.cache

import org.galio.bussantiago.domain.model.LineDetails

class LineDetailsCache(private val linesDetails: MutableMap<Int, LineDetails> = mutableMapOf()) {

  fun save(key: Int, lineDetails: LineDetails) {
    linesDetails[key] = lineDetails
  }

  fun get(key: Int): LineDetails? = linesDetails[key]
}
