package org.galio.bussantiago.domain.model

import java.util.Date

data class Incidence(
  val id: Int,
  val title: String,
  val description: String,
  val startDate: Date,
  val endDate: Date?
)
