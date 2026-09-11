package org.galio.bussantiago.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "line_details")
internal data class LineDetailsEntity(
  @PrimaryKey val id: Int,
  val code: String,
  val synoptic: String,
  val name: String,
  val information: String,
  val style: String,
  val routesJson: String
)
