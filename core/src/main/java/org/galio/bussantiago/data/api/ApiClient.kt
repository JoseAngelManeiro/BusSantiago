package org.galio.bussantiago.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.galio.bussantiago.core.Either
import org.galio.bussantiago.core.Either.Error
import org.galio.bussantiago.core.Either.Success
import org.galio.bussantiago.data.entity.BusStopRequest
import org.galio.bussantiago.data.exception.NetworkConnectionException
import org.galio.bussantiago.data.exception.ServiceException
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://app.tussa.org/tussa/api/"

internal class ApiClient(baseEndpoint: String = BASE_URL) {

  private val service: ApiService

  init {
    val interceptor = HttpLoggingInterceptor().apply {
      level = HttpLoggingInterceptor.Level.BODY
    }

    val client = OkHttpClient.Builder()
      .connectTimeout(20, TimeUnit.SECONDS) // time to establish connection (default 10)
      .readTimeout(20, TimeUnit.SECONDS)    // time to wait for server response (default 10)
      .writeTimeout(20, TimeUnit.SECONDS)   // time to send request data (default 10)
      .addInterceptor(interceptor)
      .build()

    val retrofit = Retrofit.Builder()
      .baseUrl(baseEndpoint)
      .client(client)
      .addConverterFactory(GsonConverterFactory.create())
      .build()

    service = retrofit.create(ApiService::class.java)
  }

  fun getLines() = callService {
    service.getLines()
  }

  fun getLineDetails(id: Int) = callService {
    service.getLineDetails(id)
  }

  fun getBusStopRemainingTimes(code: String) = callService {
    service.getBusStopRemainingTimes(code)
  }

  fun searchBusStop(request: BusStopRequest) = callService {
    service.searchBusStop(request)
  }

  private fun <T> callService(callback: () -> Call<T>): Either<Exception, T> {
    return try {
      val response = callback().execute()
      val responseBody = response.body()
      if (response.isSuccessful && responseBody != null) {
        Success(responseBody)
      } else {
        Error(ServiceException())
      }
    } catch (exception: IOException) {
      when (exception) {
        is UnknownHostException, is ConnectException -> Error(NetworkConnectionException())
        else -> Error(ServiceException())
      }
    }
  }
}
