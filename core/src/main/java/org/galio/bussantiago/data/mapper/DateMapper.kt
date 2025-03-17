package org.galio.bussantiago.data.mapper

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

internal class DateMapper {

  fun getDate(stringDate: String): Date? {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
    return formatter.parse(stringDate)
  }
}
