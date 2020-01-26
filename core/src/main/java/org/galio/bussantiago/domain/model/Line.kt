package org.galio.bussantiago.domain.model

data class Line(
  val id: Int,
  val code: String,
  val synoptic: String,
  val name: String,
  val company: String,
  val incidents: Int,
  val style: String
)
