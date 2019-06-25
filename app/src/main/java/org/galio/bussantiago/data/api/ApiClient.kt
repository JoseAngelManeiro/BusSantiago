package org.galio.bussantiago.data.api

import org.galio.bussantiago.common.Either
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "http://app.tussa.org/tussa/api/"

class ApiClient {

  private val service: ApiService

  init {
    val retrofit = Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()

    service = retrofit.create<ApiService>(ApiService::class.java)
  }

  fun getLines() = callService {
    service.getLines()
  }

  private fun <T> callService(callback: () -> Call<T>): Either<Exception, T> {
    val response = callback().execute()
    val responseBody = response.body()
    return if (response.isSuccessful && responseBody != null) {
      Either.Right(responseBody)
    } else {
      Either.Left(Exception())
    }
  }
}
