package org.galio.bussantiago.core

import org.galio.bussantiago.core.model.LineDetails

/**
 * Use case for retrieving the line details for a specific line id.
 *
 * If the operation fails due to network issues, a NetworkConnectionException is returned
 * in the Result. For other service-related errors, a ServiceException is returned.
 *
 * @see org.galio.bussantiago.data.exception.NetworkConnectionException
 * @see org.galio.bussantiago.data.exception.ServiceException
 */
interface GetLineDetails {
  operator fun invoke(lineId: Int): Result<LineDetails>
}
