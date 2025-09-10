package org.galio.bussantiago.core

/**
 * Use case for retrieving the information for a specific line id.
 *
 * This information is in HTML format and provides the bus schedule.
 *
 * If the operation fails due to network issues, a NetworkConnectionException is returned
 * in the Result. For other service-related errors, a ServiceException is returned.
 *
 * @see org.galio.bussantiago.data.exception.NetworkConnectionException
 * @see org.galio.bussantiago.data.exception.ServiceException
 */
interface GetLineInformation {
  operator fun invoke(lineId: Int): Result<String>
}
