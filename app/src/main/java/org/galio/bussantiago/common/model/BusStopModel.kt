package org.galio.bussantiago.common.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BusStopModel(
  val code: String,
  val name: String
) : Parcelable
