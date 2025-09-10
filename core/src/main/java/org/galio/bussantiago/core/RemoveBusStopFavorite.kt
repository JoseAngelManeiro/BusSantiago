package org.galio.bussantiago.core

import org.galio.bussantiago.core.model.BusStopFavorite

/**
 * Use case for removing a bus stop from the favorites list.
 *
 * If the operation fails, a DatabaseException is returned in the Result.
 *
 * @see org.galio.bussantiago.data.exception.DatabaseException
 */
interface RemoveBusStopFavorite {
  operator fun invoke(busStopFavorite: BusStopFavorite): Result<Unit>
}
