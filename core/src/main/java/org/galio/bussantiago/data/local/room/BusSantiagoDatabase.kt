package org.galio.bussantiago.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import org.galio.bussantiago.data.local.room.entity.BusStopEntity
import org.galio.bussantiago.data.local.room.entity.LineDetailsEntity
import org.galio.bussantiago.data.local.room.entity.LineEntity

@Database(
  entities = [
    BusStopEntity::class,
    LineEntity::class,
    LineDetailsEntity::class
  ],
  version = 1,
  exportSchema = false
)
internal abstract class BusSantiagoDatabase : RoomDatabase() {
  abstract fun busStopDao(): BusStopDao
  abstract fun lineDao(): LineDao
  abstract fun lineDetailsDao(): LineDetailsDao
}
