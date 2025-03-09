package org.galio.bussantiago.core

import org.galio.bussantiago.core.model.BusStop

interface GetLineBusStops : Interactor<GetLineBusStops.Request, List<BusStop>> {

  data class Request(
    val lineId: Int,
    val routeName: String
  )
}
