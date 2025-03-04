package org.galio.bussantiago.domain.interactor

import org.galio.bussantiago.Either
import org.galio.bussantiago.Either.Left
import org.galio.bussantiago.Either.Right
import org.galio.bussantiago.domain.model.BusStop
import org.galio.bussantiago.domain.repository.LineDetailsRepository

class GetLineBusStops(
  private val lineDetailsRepository: LineDetailsRepository
) : Interactor<GetLineBusStops.Request, List<BusStop>> {

  data class Request(
    val lineId: Int,
    val routeName: String
  )

  override fun invoke(request: Request): Either<Exception, List<BusStop>> {
    val response = lineDetailsRepository.getLineDetails(request.lineId)
    return if (response.isRight) {
      val routeByName = response.rightValue.routes.find { it.name == request.routeName }!!
      Right(routeByName.busStops)
    } else {
      Left(response.leftValue)
    }
  }
}
