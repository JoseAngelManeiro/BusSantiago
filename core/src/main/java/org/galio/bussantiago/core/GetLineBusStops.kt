package org.galio.bussantiago.core

import org.galio.bussantiago.core.model.BusStop

/**
 * Use case for retrieving the bus stops for a specific bus line and route.
 *
 * If the route send as input param does not exist in the line an empty list of routes is returned.
 *
 * If the operation fails due to network issues, a NetworkConnectionException is returned
 * in the Result. For other service-related errors, a ServiceException is returned.
 *
 * @see org.galio.bussantiago.data.exception.NetworkConnectionException
 * @see org.galio.bussantiago.data.exception.ServiceException
 */
interface GetLineBusStops {

  data class Request(
    val lineId: Int,
    val routeName: String
  )

  operator fun invoke(request: Request): Result<List<BusStop>>
}
