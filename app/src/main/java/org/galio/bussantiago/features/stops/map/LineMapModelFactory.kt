package org.galio.bussantiago.features.stops.map

import org.galio.bussantiago.core.model.LineDetails

class LineMapModelFactory {

  fun createLineMapModelFactory(
    routeName: String,
    lineDetails: LineDetails
  ): LineMapModel? {
    return lineDetails.routes.find { it.name == routeName }?.let { route ->
      LineMapModel(
        lineStyle = lineDetails.style,
        busStopMapModels = route.busStops.map {
          BusStopMapModel(
            code = it.code,
            name = it.name,
            coordinates = it.coordinates
          )
        }
      )
    }
  }
}
