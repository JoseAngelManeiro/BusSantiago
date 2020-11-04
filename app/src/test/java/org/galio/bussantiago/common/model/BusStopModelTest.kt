package org.galio.bussantiago.common.model

import org.junit.Assert.assertTrue
import org.junit.Test

class BusStopModelTest {

  @Test
  fun `is not valid if all of its params are empty`() {
    val busStopModel = BusStopModel(code = "", name = "")

    assertTrue(busStopModel.isNotValid())
  }
}
