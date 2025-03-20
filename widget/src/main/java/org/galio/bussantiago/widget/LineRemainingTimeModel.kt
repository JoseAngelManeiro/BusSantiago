package org.galio.bussantiago.widget

data class LineRemainingTimeModel(
  val synopticModel: SynopticModel,
  val minutesUntilNextArrival: Int
)
