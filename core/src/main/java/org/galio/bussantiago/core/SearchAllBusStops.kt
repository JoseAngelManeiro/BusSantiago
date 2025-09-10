package org.galio.bussantiago.core

import org.galio.bussantiago.core.model.BusStopSearch

/**
 * Use case for retrieving all existing bus stops without repeats.
 *
 * If the operation fails due to network issues, a NetworkConnectionException is returned
 * in the Result. For other service-related errors, a ServiceException is returned.
 *
 * @see org.galio.bussantiago.data.exception.NetworkConnectionException
 * @see org.galio.bussantiago.data.exception.ServiceException
 */
interface SearchAllBusStops {
  operator fun invoke(): Result<List<BusStopSearch>>
}
