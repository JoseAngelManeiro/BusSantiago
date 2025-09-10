package org.galio.bussantiago.core

import org.galio.bussantiago.core.model.BusStopRemainingTimes

/**
 * Use case for retrieving the remaining arrival times for a specific bus stop.
 *
 * If the operation fails due to network issues, a NetworkConnectionException is returned
 * in the Result. For other service-related errors, a ServiceException is returned.
 *
 * @see org.galio.bussantiago.data.exception.NetworkConnectionException
 * @see org.galio.bussantiago.data.exception.ServiceException
 */
interface GetBusStopRemainingTimes {
  operator fun invoke(busStopCode: String): Result<BusStopRemainingTimes>
}
