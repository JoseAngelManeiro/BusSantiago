package org.galio.bussantiago.features.times

import org.galio.bussantiago.common.ui.SynopticModel

data class LineRemainingTimeModel(
  val synopticModel: SynopticModel,
  val minutesUntilNextArrival: Int
)
