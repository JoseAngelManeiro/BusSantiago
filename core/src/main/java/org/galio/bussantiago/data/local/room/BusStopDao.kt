package org.galio.bussantiago.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface BusStopDao {

  @Query("SELECT * FROM bus_stop")
  fun getAll(): List<BusStopEntity>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(busStops: List<BusStopEntity>)

  @Query("DELETE FROM bus_stop")
  fun deleteAll()

  @Transaction
  fun clearAndInsertAll(busStops: List<BusStopEntity>) {
    deleteAll()
    insertAll(busStops)
  }
}
