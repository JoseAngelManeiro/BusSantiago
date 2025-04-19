package org.galio.bussantiago.shared

import org.galio.bussantiago.core.model.BusStopRemainingTimes

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
