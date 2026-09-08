package org.galio.bussantiago.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bus_stop")
internal data class BusStopEntity(
  @PrimaryKey val id: Int,
  val code: String,
  val name: String,
  val zone: String?,
  val latitude: Double,
  val longitude: Double,
  val linesJson: String
)
