package org.galio.bussantiago.widget

import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ObtainJson {

  private val TAG = this::class.java.simpleName

  fun call(urlString: String): String? {

    var urlConnection: HttpURLConnection? = null
    var reader: BufferedReader? = null
    var callJsonString: String?

    try {
      val url = URL(urlString)
      urlConnection = url.openConnection() as HttpURLConnection
      urlConnection.requestMethod = "GET"
      urlConnection.connectTimeout = 3000
      urlConnection.readTimeout = 7000
      urlConnection.connect()

      // Read the input stream into a String
      val inputStream = urlConnection.inputStream
      val buffer = StringBuffer()
      if (inputStream == null) { // Nothing to do.
        return null
      }

      reader = BufferedReader(InputStreamReader(inputStream))
      var line = reader.readLine()
      while (line != null) {
        buffer.append(line + "\n")
        line = reader.readLine()
      }

      if (buffer.isEmpty()) {
        return null
      }

      callJsonString = buffer.toString()
    } catch (e: IOException) {
      callJsonString = null
      Log.e(TAG, e.message)
    } finally {
      urlConnection?.disconnect()
      if (reader != null) {
        try {
          reader.close()
        } catch (e: IOException) {
          Log.e(TAG, "Error closing stream", e)
        }
      }
    }
    return callJsonString
  }
}
