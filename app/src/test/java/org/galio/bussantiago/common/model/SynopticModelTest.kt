package org.galio.bussantiago.common.model

import org.junit.Assert.assertEquals
import org.junit.Test

class SynopticModelTest {

  @Test
  fun `when synoptic prefix is 'L' should return the rest of the synoptic value`() {
    val synopticModel = SynopticModel(synoptic = "LC11", style = "")

    assertEquals("C11", synopticModel.getSynopticFormatted())
  }

  @Test
  fun `when synoptic prefix is not 'L' should return all the synoptic value`() {
    val synopticModel = SynopticModel(synoptic = "C11", style = "")

    assertEquals("C11", synopticModel.getSynopticFormatted())
  }
}
