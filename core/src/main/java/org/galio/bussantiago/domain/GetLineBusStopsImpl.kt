package org.galio.bussantiago.domain

import org.galio.bussantiago.Either
import org.galio.bussantiago.Either.Left
import org.galio.bussantiago.Either.Right
import org.galio.bussantiago.core.GetLineBusStops
import org.galio.bussantiago.core.model.BusStop
import org.galio.bussantiago.data.repository.LineDetailsRepository

internal class GetLineBusStopsImpl(
  private val lineDetailsRepository: LineDetailsRepository
) : GetLineBusStops {

  override fun invoke(request: GetLineBusStops.Request): Either<Exception, List<BusStop>> {
    val response = lineDetailsRepository.getLineDetails(request.lineId)
    return if (response.isRight) {
      val routeByName = response.rightValue.routes.find { it.name == request.routeName }!!
      Right(routeByName.busStops)
    } else {
      Left(response.leftValue)
    }
  }
}
