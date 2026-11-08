package org.galio.bussantiago.shared

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TimeFormatter {

  fun getDescription(minutesUntilNextArrival: Int): String {
    return when {
      minutesUntilNextArrival == 0 -> "en parada"
      minutesUntilNextArrival == 60 -> "1 h"
      minutesUntilNextArrival > 60 -> {
        val numHours = minutesUntilNextArrival / 60
        val numMinutes = minutesUntilNextArrival - numHours * 60
        "$numHours h $numMinutes min"
      }
      else -> "$minutesUntilNextArrival min"
    }
  }

  fun formatTime(date: Date = Date()): String {
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return formatter.format(date)
  }
}

