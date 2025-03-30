package org.galio.bussantiago.shared

import android.content.Context

object DeeplinkHelper {

  const val BUS_STOP_CODE_KEY = "bus_stop_code"
  const val BUS_STOP_NAME_KEY = "bus_stop_name"

  fun getTimesDeeplink(context: Context, code: String, name: String): String {
    val scheme = context.getString(R.string.app_scheme)
    val host = context.getString(R.string.times_host)
    return "$scheme://$host/$code/$name"
  }
}
