package org.galio.bussantiago.features.times

import org.galio.bussantiago.common.model.SynopticModel
import org.galio.bussantiago.domain.model.BusStopRemainingTimes

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
