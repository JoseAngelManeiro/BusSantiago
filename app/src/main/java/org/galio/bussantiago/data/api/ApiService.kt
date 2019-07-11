package org.galio.bussantiago.data.api

import org.galio.bussantiago.data.entity.LineDetailsEntity
import org.galio.bussantiago.data.entity.LineEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

  @GET("lineas")
  fun getLines(): Call<List<LineEntity>>

  @GET("lineas/{id}?lang=es")
  fun getLineDetails(@Path("id") id: Int): Call<LineDetailsEntity>
}
