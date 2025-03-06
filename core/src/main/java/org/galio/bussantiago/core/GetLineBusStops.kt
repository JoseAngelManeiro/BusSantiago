package org.galio.bussantiago.core

import org.galio.bussantiago.domain.interactor.Interactor
import org.galio.bussantiago.domain.model.BusStop

interface GetLineBusStops : Interactor<GetLineBusStops.Request, List<BusStop>> {

  data class Request(
    val lineId: Int,
    val routeName: String
  )
}
