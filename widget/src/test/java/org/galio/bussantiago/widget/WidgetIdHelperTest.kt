package org.galio.bussantiago.widget

import android.appwidget.AppWidgetManager
import android.content.Intent
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class WidgetIdHelperTest {

  private val widgetIdHelper = WidgetIdHelper()

  @Test
  fun `when there is no id saved should return an invalid value`() {
    val emptyIntent = Intent()

    val result = widgetIdHelper.getWidgetId(emptyIntent)

    assertEquals(AppWidgetManager.INVALID_APPWIDGET_ID, result)
  }

  @Test
  fun `when there is an id saved should return the value`() {
    val widgetId = 123
    val intent = Intent().apply {
      putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
    }

    val result = widgetIdHelper.getWidgetId(intent)

    assertEquals(widgetId, result)
  }

  @Test
  fun `when the id is not invalid isWidgetIdValid should return true`() {
    val widgetId = 1

    assertTrue(widgetIdHelper.isWidgetIdValid(widgetId))
  }

  @Test
  fun `when the id is invalid isWidgetIdValid should return false`() {
    val widgetId = AppWidgetManager.INVALID_APPWIDGET_ID // 0

    assertFalse(widgetIdHelper.isWidgetIdValid(widgetId))
  }
}
