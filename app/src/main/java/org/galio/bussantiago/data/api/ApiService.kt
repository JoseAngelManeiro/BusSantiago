package org.galio.bussantiago.data.api

import org.galio.bussantiago.data.entity.LineEntity
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

  @GET("lineas")
  fun getLines(): Call<List<LineEntity>>
}
