package org.galio.bussantiago.data.api

import org.galio.bussantiago.common.Either.Right
import org.galio.bussantiago.common.Either.Left
import org.galio.bussantiago.common.Either
import org.galio.bussantiago.common.exception.NetworkConnectionException
import org.galio.bussantiago.common.exception.ServiceException
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

private const val BASE_URL = "http://app.tussa.org/tussa/api/"

class ApiClient(private val networkHandler: NetworkHandler) {

  private val service: ApiService

  init {
    val retrofit = Retrofit.Builder()
      .baseUrl(BASE_URL)
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
