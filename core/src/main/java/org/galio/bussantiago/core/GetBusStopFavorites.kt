package org.galio.bussantiago.core

import org.galio.bussantiago.core.model.BusStopFavorite

/**
 * Use case for getting the list of favorites.
 *
 * If the operation fails, a DatabaseException is returned in the Result.
 *
 * @see org.galio.bussantiago.data.exception.DatabaseException
 */
interface GetBusStopFavorites {
  operator fun invoke(): Result<List<BusStopFavorite>>
}
