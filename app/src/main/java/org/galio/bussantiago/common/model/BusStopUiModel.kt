package org.galio.bussantiago.common.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.galio.bussantiago.core.model.BusStopSearch

@Parcelize
data class BusStopUiModel(
    val code: String,
    val name: String,
    val lines: List<LineUiModel>
) : Parcelable

@Parcelize
data class LineUiModel(
    val synoptic: String,
    val style: String
) : Parcelable
