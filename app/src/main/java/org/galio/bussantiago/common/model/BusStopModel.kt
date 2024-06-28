package org.galio.bussantiago.common.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BusStopModel(
  val code: String,
  val name: String
) : Parcelable {

  fun isNotValid() = code.isEmpty() && name.isEmpty()
}
