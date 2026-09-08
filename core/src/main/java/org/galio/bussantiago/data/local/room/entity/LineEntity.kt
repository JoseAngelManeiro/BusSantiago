package org.galio.bussantiago.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "line")
internal data class LineEntity(
  @PrimaryKey val id: Int,
  val code: String,
  val synoptic: String,
  val name: String,
  val company: String,
  val incidents: Int,
  val style: String
)
