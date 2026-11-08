package org.galio.bussantiago.shared

import org.junit.Assert.assertEquals
import org.junit.Test

class TimeFormatterTest {

  private val timeFormatter = TimeFormatter()

  @Test
  fun `if there are 0 mins until next arrival, the literal 'en parada' is returned`() {
    assertEquals("en parada", timeFormatter.getDescription(0))
  }

  @Test
  fun `if there are less than 59 minutes until next arrival, formatted time is returned`() {
    assertEquals("48 min", timeFormatter.getDescription(48))
  }

  @Test
  fun `if there are exactly 60 minutes until next arrival, formatted time is returned`() {
    assertEquals("1 h", timeFormatter.getDescription(60))
  }

  @Test
  fun `if there are more than 60 minutes until next arrival, formatted time is returned`() {
    assertEquals("1 h 15 min", timeFormatter.getDescription(75))
  }

  @Test
  fun `formatTime returns the expected HH mm string`() {
    val calendar = java.util.Calendar.getInstance().apply {
      set(java.util.Calendar.HOUR_OF_DAY, 14)
      set(java.util.Calendar.MINUTE, 30)
    }
    assertEquals("14:30", timeFormatter.formatTime(calendar.time))
  }
}
