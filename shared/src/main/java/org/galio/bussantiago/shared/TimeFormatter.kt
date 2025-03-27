package org.galio.bussantiago.shared

fun getDescriptionByMinutes(minutesUntilNextArrival: Int): String {
  return when {
    minutesUntilNextArrival == 0 -> "llegando"
    minutesUntilNextArrival == -1 -> "<1 min"
    minutesUntilNextArrival == -2 -> "en parada"
    minutesUntilNextArrival > 59 -> {
      val numHours = minutesUntilNextArrival / 60
      val numMinutes = minutesUntilNextArrival - numHours * 60
      "$numHours h $numMinutes min"
    }

    else -> "$minutesUntilNextArrival min"
  }
}
