package org.galio.bussantiago.core.model

import com.google.gson.annotations.SerializedName

data class BusStop(
  @SerializedName("id")
  val id: Int,
  @SerializedName("code")
  val code: String,
  @SerializedName("name")
  val name: String,
  @SerializedName("zone")
  val zone: String?,
  @SerializedName("extraordinary")
  val extraordinary: Boolean,
  @SerializedName("coordinates")
  val coordinates: Coordinates
)
