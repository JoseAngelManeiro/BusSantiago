package org.galio.bussantiago.core.model

import com.google.gson.annotations.SerializedName

data class LineSearch(
  @SerializedName("synoptic")
  val synoptic: String,
  @SerializedName("style")
  val style: String
)
