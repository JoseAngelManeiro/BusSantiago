package org.galio.bussantiago.features.search

import org.galio.bussantiago.core.model.BusStopSearch
import java.text.Normalizer

class SearchUtils {

  fun filterBusStops(
    busStops: List<BusStopSearch>,
    query: String
  ): List<BusStopSearch> {
    if (query.isBlank()) return emptyList()

    val normalizedQuery = removeAccents(query.lowercase()).trim()
    val tokens = normalizedQuery.split("\\s+".toRegex())

    return busStops.filter { stop ->
      val searchableText = removeAccents("${stop.code} ${stop.name}".lowercase())
      tokens.all { token -> searchableText.contains(token) }
    }
  }

  private fun removeAccents(input: String): String {
    val normalized = Normalizer.normalize(input, Normalizer.Form.NFD)
    return normalized.replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
  }
}
