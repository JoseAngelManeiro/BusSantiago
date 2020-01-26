package org.galio.bussantiago.features.times

import org.galio.bussantiago.common.model.SynopticModel
import org.galio.bussantiago.domain.model.BusStopRemainingTimes

class TimesFactory {

  fun createLineRemainingTimeModels(
    busStopRemainingTimes: BusStopRemainingTimes
  ): List<LineRemainingTimeModel> {
    return busStopRemainingTimes.lineRemainingTimes.map {
      LineRemainingTimeModel(
        SynopticModel(it.synoptic, it.style),
        it.minutesUntilNextArrival
      )
    }
  }
}
