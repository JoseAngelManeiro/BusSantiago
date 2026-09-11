package org.galio.bussantiago.common.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LineUiModel(
  val synoptic: String,
  val style: String
) : Parcelable
