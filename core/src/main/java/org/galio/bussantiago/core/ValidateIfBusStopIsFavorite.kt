package org.galio.bussantiago.core

/**
 * Use case to validate if a stop is a favorite (true) or not (false) based on its bus code.
 *
 * If the operation fails, a DatabaseException is returned in the Result.
 *
 * @see org.galio.bussantiago.data.exception.DatabaseException
 */
interface ValidateIfBusStopIsFavorite {
  operator fun invoke(busStopCode: String): Result<Boolean>
}
