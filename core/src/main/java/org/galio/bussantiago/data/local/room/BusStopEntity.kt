package org.galio.bussantiago.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bus_stop")
data class BusStopEntity(
  @PrimaryKey val id: Int,
  val code: String,
  val name: String,
  val zone: String?,
  val latitude: Double,
  val longitude: Double,
  val linesJson: String
)
