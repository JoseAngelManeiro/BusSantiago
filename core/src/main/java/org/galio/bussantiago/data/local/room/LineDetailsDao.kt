package org.galio.bussantiago.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.galio.bussantiago.data.local.room.entity.LineDetailsEntity

@Dao
internal interface LineDetailsDao {

  @Query("SELECT * FROM line_details WHERE id = :id")
  fun get(id: Int): LineDetailsEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(lineDetails: LineDetailsEntity)
}
