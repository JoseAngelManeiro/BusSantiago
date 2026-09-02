package org.galio.bussantiago.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface LineDao {

  @Query("SELECT * FROM line")
  fun getAll(): List<LineEntity>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(lines: List<LineEntity>)

  @Query("DELETE FROM line")
  fun deleteAll()

  @Transaction
  fun clearAndInsertAll(lines: List<LineEntity>) {
    deleteAll()
    insertAll(lines)
  }
}
