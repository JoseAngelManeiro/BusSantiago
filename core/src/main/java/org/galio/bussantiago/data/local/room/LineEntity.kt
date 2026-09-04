package org.galio.bussantiago.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "line")
data class LineEntity(
  @PrimaryKey val id: Int,
  val code: String,
  val synoptic: String,
  val name: String,
  val company: String,
  val incidents: Int,
  val style: String
)
