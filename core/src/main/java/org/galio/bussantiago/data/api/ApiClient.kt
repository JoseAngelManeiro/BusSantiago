package org.galio.bussantiago.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.galio.bussantiago.Either
import org.galio.bussantiago.Either.Left
import org.galio.bussantiago.Either.Right
import org.galio.bussantiago.data.entity.BusStopRequest
import org.galio.bussantiago.exception.NetworkConnectionException
import org.galio.bussantiago.exception.ServiceException
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

private const val BASE_URL = "https://app.tussa.org/tussa/api/"

class ApiClient(private val networkHandler: NetworkHandler) {

  private val service: ApiService

  init {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    val retrofit = Retrofit.Builder()
      .baseUrl(BASE_URL)
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
    return if (networkHandler.isConnected()) {
      try {
        val response = callback().execute()
        val responseBody = response.body()
        if (response.isSuccessful && responseBody != null) {
          Right(responseBody)
        } else {
          Left(ServiceException())
        }
      } catch (exception: IOException) {
        Left(ServiceException())
      }
    } else {
      Left(NetworkConnectionException())
    }
  }
}
