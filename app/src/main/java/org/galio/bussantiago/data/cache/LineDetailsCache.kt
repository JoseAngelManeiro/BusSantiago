package org.galio.bussantiago.data.cache

import android.util.SparseArray
import org.galio.bussantiago.domain.model.LineDetails

class LineDetailsCache(private val linesDetails: SparseArray<LineDetails> = SparseArray()) {

  fun save(key: Int, lineDetails: LineDetails) {
    linesDetails.put(key, lineDetails)
  }

  fun get(key: Int): LineDetails? = linesDetails.get(key)
}
