package org.galio.bussantiago.data.cache

import org.galio.bussantiago.domain.model.BusStopSearch

class BusStopSearchCache(private val busStops: MutableList<BusStopSearch> = mutableListOf()) {

  fun save(busStops: List<BusStopSearch>) {
    this.busStops.clear()
    this.busStops.addAll(busStops)
  }

  fun getAll(): List<BusStopSearch> = busStops.toList()
}
