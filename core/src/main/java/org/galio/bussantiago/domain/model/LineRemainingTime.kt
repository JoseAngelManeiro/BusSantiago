package org.galio.bussantiago.domain.model

import java.util.Date

data class LineRemainingTime(
  val id: Int,
  val synoptic: String,
  val name: String,
  val style: String,
  val nextArrival: Date,
  val minutesUntilNextArrival: Int
)
