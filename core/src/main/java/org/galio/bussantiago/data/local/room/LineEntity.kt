package org.galio.bussantiago.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.galio.bussantiago.core.model.Line

@Entity(tableName = "line")
data class LineEntity(
  @PrimaryKey val id: Int,
  val code: String,
  val synoptic: String,
  val name: String,
  val company: String,
  val incidents: Int,
  val style: String
) {
  fun toDomain(): Line = Line(
    id = id,
    code = code,
    synoptic = synoptic,
    name = name,
    company = company,
    incidents = incidents,
    style = style
  )
}

fun Line.toEntity(): LineEntity = LineEntity(
  id = id,
  code = code,
  synoptic = synoptic,
  name = name,
  company = company,
  incidents = incidents,
  style = style
)
