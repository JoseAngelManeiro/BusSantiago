package org.galio.bussantiago.core

import org.galio.bussantiago.core.model.BusStopSearch

interface SearchAllBusStops {
  operator fun invoke(): Result<List<BusStopSearch>>
}
