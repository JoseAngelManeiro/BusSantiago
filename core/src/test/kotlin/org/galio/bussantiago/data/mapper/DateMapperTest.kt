package org.galio.bussantiago.data.mapper

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Locale

class DateMapperTest {

  private val dateMapper = DateMapper()

  @Test
  fun `should map applying the formatter expected`() {
    val textDate = "2025-03-14 18:26"
    val expected = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US).parse(textDate)

    val result = dateMapper.getDate(textDate)

    assertEquals(expected, result)
  }
}
