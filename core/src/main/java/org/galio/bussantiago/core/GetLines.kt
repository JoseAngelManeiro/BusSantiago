package org.galio.bussantiago.core

import org.galio.bussantiago.core.model.Line

/**
 * Use case for retrieving all the bus lines.
 *
 * If the operation fails due to network issues, a NetworkConnectionException is returned
 * in the Result. For other service-related errors, a ServiceException is returned.
 *
 * @see org.galio.bussantiago.data.exception.NetworkConnectionException
 * @see org.galio.bussantiago.data.exception.ServiceException
 */
interface GetLines {
  operator fun invoke(): Result<List<Line>>
}
