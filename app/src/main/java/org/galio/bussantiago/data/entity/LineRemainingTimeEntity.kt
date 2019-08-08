package org.galio.bussantiago.data.entity

data class LineRemainingTimeEntity(
  val id: Int,
  val sinoptico: String,
  val nombre: String,
  val estilo: String,
  val proximoPaso: String,
  val minutosProximoPaso: Int
)
