package org.galio.bussantiago.shared

import org.galio.bussantiago.core.model.BusStopRemainingTimes
import org.galio.bussantiago.core.model.Coordinates
import org.galio.bussantiago.core.model.LineRemainingTime
import org.junit.Assert.assertEquals
import org.junit.Test

class TimesFactoryTest {

  private val timesFactory = TimesFactory()

  @Test
  fun `should create the list of models as expected`() {
    val lineRemainingTime1 = lineRemainingTime.copy(
      synoptic = "Synoptic1",
      style = "Style1",
      minutesUntilNextArrival = 11
    )
    val lineRemainingTime2 = lineRemainingTime.copy(
      synoptic = "Synoptic2",
      style = "Style2",
      minutesUntilNextArrival = 12
    )

    val result = timesFactory.createLineRemainingTimeModels(
      busStopRemainingTimes.copy(
        lineRemainingTimes = listOf(lineRemainingTime1, lineRemainingTime2)
      )
    )

    val listExpected = listOf(
      LineRemainingTimeModel(
        synopticModel = SynopticModel("Synoptic1", "Style1"),
        minutesUntilNextArrival = 11
      ),
      LineRemainingTimeModel(
        synopticModel = SynopticModel("Synoptic2", "Style2"),
        minutesUntilNextArrival = 12
      )
    )

    assertEquals(listExpected, result)
  }

  private val busStopRemainingTimes = BusStopRemainingTimes(
    id = 0,
    code = "",
    name = "",
    zone = "",
    coordinates = Coordinates(0.0, 0.0),
    lineRemainingTimes = emptyList()
  )

  private val lineRemainingTime = LineRemainingTime(
    id = 0,
    synoptic = "",
    name = "",
    style = "",
    nextArrival = null,
    minutesUntilNextArrival = 0
  )
}
