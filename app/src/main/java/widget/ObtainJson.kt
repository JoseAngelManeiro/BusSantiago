package widget

import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
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
      urlConnection.connectTimeout = 4000
      urlConnection.readTimeout = 8000
      urlConnection.connect()

      // Read the input stream into a String
      val inputStream = urlConnection.inputStream ?: return null // Nothing to do
      reader = BufferedReader(InputStreamReader(inputStream))
      val buffer = StringBuffer()
      var line = reader.readLine()
      while (line != null) {
        buffer.append(line).append("\n")
        line = reader.readLine()
      }

      if (buffer.isEmpty()) {
        return null
      }

      callJsonString = buffer.toString()
    } catch (e: SocketTimeoutException) {
      callJsonString = null
      Log.e(TAG, e.message.toString())
    } catch (e: IOException) {
      callJsonString = null
      Log.e(TAG, e.message.toString())
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
