package org.galio.bussantiago.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
  entities = [
    BusStopEntity::class,
    LineEntity::class,
    LineDetailsEntity::class
  ],
  version = 1,
  exportSchema = false
)
abstract class BusSantiagoDatabase : RoomDatabase() {
  abstract fun busStopDao(): BusStopDao
  abstract fun lineDao(): LineDao
  abstract fun lineDetailsDao(): LineDetailsDao
}
