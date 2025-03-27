package org.galio.bussantiago.features.times

import org.galio.bussantiago.core.model.BusStopRemainingTimes
import org.galio.bussantiago.shared.LineRemainingTimeModel
import org.galio.bussantiago.shared.SynopticModel

class TimesFactory {

  fun createLineRemainingTimeModels(
    busStopRemainingTimes: BusStopRemainingTimes
  ): List<LineRemainingTimeModel> {
    return busStopRemainingTimes.lineRemainingTimes.map {
      LineRemainingTimeModel(
        synopticModel = SynopticModel(it.synoptic, it.style),
        minutesUntilNextArrival = it.minutesUntilNextArrival
      )
    }
  }
}
