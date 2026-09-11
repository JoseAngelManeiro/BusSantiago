package org.galio.bussantiago.core.model

import com.google.gson.annotations.SerializedName

data class Route(
  @SerializedName("name")
  val name: String,
  @SerializedName("direction")
  val direction: String,
  @SerializedName("busStops")
  val busStops: List<BusStop>
)
