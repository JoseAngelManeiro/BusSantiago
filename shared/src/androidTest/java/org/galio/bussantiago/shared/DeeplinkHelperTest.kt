package org.galio.bussantiago.shared

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DeeplinkHelperTest {

  @Test
  fun shouldReturnDeeplinkExpected() {
    val result = DeeplinkHelper.getTimesDeeplink(
      context = ApplicationProvider.getApplicationContext(),
      code = "L1",
      name = "Praza Galicia"
    )

    assertEquals("bussantiago://times/L1/Praza Galicia", result)
  }
}
