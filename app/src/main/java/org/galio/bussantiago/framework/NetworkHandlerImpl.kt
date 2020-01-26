package org.galio.bussantiago.framework

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import org.galio.bussantiago.data.api.NetworkHandler

class NetworkHandlerImpl(private val context: Context) : NetworkHandler {

  override fun isConnected(): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
    return if (connectivityManager is ConnectivityManager) {
      val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
      networkInfo?.isConnected ?: false
    } else false
  }
}
