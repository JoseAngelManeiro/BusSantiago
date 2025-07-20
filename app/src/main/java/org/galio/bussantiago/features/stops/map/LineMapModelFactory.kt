package org.galio.bussantiago.features.stops.map

import org.galio.bussantiago.core.model.LineDetails

class LineMapModelFactory {

  fun createLineMapModelFactory(
    routeName: String,
    lineDetails: LineDetails
  ): LineMapModel {
    // TODO: Review why we're forced to use "!!" operator
    val routeByName = lineDetails.routes.find { it.name == routeName }!!
    return LineMapModel(
      lineStyle = lineDetails.style,
      busStopMapModels = routeByName.busStops.map {
        BusStopMapModel(
          code = it.code,
          name = it.name,
          coordinates = it.coordinates
        )
      }
    )
  }
}
