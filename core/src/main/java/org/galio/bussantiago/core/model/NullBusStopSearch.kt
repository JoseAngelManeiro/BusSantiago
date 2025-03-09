package org.galio.bussantiago.core.model

class NullBusStopSearch : BusStopSearch(
  id = 0,
  code = "",
  name = "",
  zone = null,
  coordinates = Coordinates(0.0, 0.0),
  lines = emptyList()
)
