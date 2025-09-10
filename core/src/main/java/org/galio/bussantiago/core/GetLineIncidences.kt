package org.galio.bussantiago.core

import org.galio.bussantiago.core.model.Incidence

/**
 * Use case for retrieving the incidences for a specific line id,
 * or an empty list if there are none.
 *
 * If the operation fails due to network issues, a NetworkConnectionException is returned
 * in the Result. For other service-related errors, a ServiceException is returned.
 *
 * @see org.galio.bussantiago.data.exception.NetworkConnectionException
 * @see org.galio.bussantiago.data.exception.ServiceException
 */
interface GetLineIncidences {
  operator fun invoke(lineId: Int): Result<List<Incidence>>
}
