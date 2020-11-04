package org.galio.bussantiago.features.times

import org.galio.bussantiago.common.model.SynopticModel

data class LineRemainingTimeModel(
  val synopticModel: SynopticModel,
  val minutesUntilNextArrival: Int
)
