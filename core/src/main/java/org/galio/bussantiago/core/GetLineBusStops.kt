package org.galio.bussantiago.core

import org.galio.bussantiago.core.model.BusStop

interface GetLineBusStops {

  data class Request(
    val lineId: Int,
    val routeName: String
  )

  operator fun invoke(request: Request): Result<List<BusStop>>
}
