package org.galio.bussantiago.domain.model

class NullBusStopSearch : BusStopSearch(
  id = 0,
  code = "",
  name = "",
  zone = null,
  coordinates = Coordinates(0.0, 0.0),
  lines = emptyList()
)
