package org.galio.bussantiago.features.stops

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BusStopsArgs(
  val lineId: Int,
  val routeName: String
) : Parcelable
