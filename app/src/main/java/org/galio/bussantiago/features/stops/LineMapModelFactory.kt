package org.galio.bussantiago.features.stops

import org.galio.bussantiago.domain.model.LineDetails

class LineMapModelFactory {

  fun createLineMapModelFactory(
    routeName: String,
    lineDetails: LineDetails
  ): LineMapModel {
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
