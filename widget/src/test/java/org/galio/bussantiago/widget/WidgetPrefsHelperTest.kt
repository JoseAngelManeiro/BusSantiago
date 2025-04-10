package org.galio.bussantiago.widget

import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class WidgetPrefsHelperTest {

  private lateinit var widgetPrefsHelper: WidgetPrefsHelper

  @Before
  fun setUp() {
    widgetPrefsHelper = WidgetPrefsHelper(ApplicationProvider.getApplicationContext())
  }

  @Test
  fun `when there is no data saved should return an empty string`() {
    val widgetId = 1

    assertEquals("", widgetPrefsHelper.getCode(widgetId))
    assertEquals("", widgetPrefsHelper.getName(widgetId))
    assertEquals("", widgetPrefsHelper.getHour(widgetId))
  }

  @Test
  fun `when there is data saved should return the data by widget id`() {
    val widgetId = 1

    widgetPrefsHelper.save(
      code = "L1",
      name = "Praza Galicia",
      hour = "22:42",
      widgetId = widgetId
    )

    assertEquals("L1", widgetPrefsHelper.getCode(widgetId))
    assertEquals("Praza Galicia", widgetPrefsHelper.getName(widgetId))
    assertEquals("22:42", widgetPrefsHelper.getHour(widgetId))
  }

  @Test
  fun `when the data is removed should return an empty string`() {
    val widgetId = 1

    widgetPrefsHelper.save(
      code = "L1",
      name = "Praza Galicia",
      hour = "22:42",
      widgetId = widgetId
    )

    widgetPrefsHelper.remove(widgetId)

    assertEquals("", widgetPrefsHelper.getCode(widgetId))
    assertEquals("", widgetPrefsHelper.getName(widgetId))
    assertEquals("", widgetPrefsHelper.getHour(widgetId))
  }
}
