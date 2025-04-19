package org.galio.bussantiago.domain

import org.galio.bussantiago.core.Either
import org.galio.bussantiago.core.Either.Error
import org.galio.bussantiago.core.Either.Success
import org.galio.bussantiago.core.GetLineBusStops
import org.galio.bussantiago.core.model.BusStop
import org.galio.bussantiago.data.repository.LineDetailsRepository

internal class GetLineBusStopsImpl(
  private val lineDetailsRepository: LineDetailsRepository
) : GetLineBusStops {

  override fun invoke(request: GetLineBusStops.Request): Either<Exception, List<BusStop>> {
    val response = lineDetailsRepository.getLineDetails(request.lineId)
    return if (response.isSuccess) {
      val routeByName = response.successValue.routes.find { it.name == request.routeName }!!
      Success(routeByName.busStops)
    } else {
      Error(response.errorValue)
    }
  }
}
