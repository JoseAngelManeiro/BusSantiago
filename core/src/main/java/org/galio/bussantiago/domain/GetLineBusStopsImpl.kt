package org.galio.bussantiago.domain

import org.galio.bussantiago.core.GetLineBusStops
import org.galio.bussantiago.core.model.BusStop
import org.galio.bussantiago.data.repository.LineDetailsRepository

internal class GetLineBusStopsImpl(
  private val lineDetailsRepository: LineDetailsRepository
) : GetLineBusStops {

  override fun invoke(request: GetLineBusStops.Request): Result<List<BusStop>> {
    return lineDetailsRepository.getLineDetails(request.lineId).map { lineDetails ->
      lineDetails.routes.find {
        it.name == request.routeName
      }?.busStops.orEmpty()
    }
  }
}
