package org.galio.bussantiago.shared

data class LineRemainingTimeModel(
  val synopticModel: SynopticModel,
  val minutesUntilNextArrival: Int
)
