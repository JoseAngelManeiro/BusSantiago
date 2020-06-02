package org.galio.bussantiago.data.api

import org.galio.bussantiago.data.entity.BusStopRemainingTimesEntity
import org.galio.bussantiago.data.entity.BusStopRequest
import org.galio.bussantiago.data.entity.LineDetailsEntity
import org.galio.bussantiago.data.entity.LineEntity
import org.galio.bussantiago.data.entity.SearchBusStopEntity
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

  @GET("lineas")
  fun getLines(): Call<List<LineEntity>>

  @GET("lineas/{id}?lang=es")
  fun getLineDetails(@Path("id") id: Int): Call<LineDetailsEntity>

  @GET("lineas/0/parada/{code}")
  fun getBusStopRemainingTimes(@Path("code") code: String): Call<BusStopRemainingTimesEntity>

  @Headers("Content-Type: application/json;charset=UTF-8")
  @POST("paradas")
  fun searchBusStop(@Body body: BusStopRequest): Call<List<SearchBusStopEntity>>
}
