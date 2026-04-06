package org.galio.bussantiago.shared

import android.content.Context

object DeeplinkHelper {

  fun getTimesDeeplink(context: Context, code: String, name: String): String {
    val scheme = context.getString(R.string.app_scheme)
    val host = context.getString(R.string.times_host)
    return "$scheme://$host/$code/$name"
  }
}
