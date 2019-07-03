package org.galio.bussantiago.data.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkHandler(private val context: Context) {

  fun isConnected(): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
    return if (connectivityManager is ConnectivityManager) {
      val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
      networkInfo?.isConnected ?: false
    } else false
  }
}
