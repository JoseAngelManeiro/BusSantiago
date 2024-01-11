package org.galio.bussantiago.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.core.text.HtmlCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

fun ViewGroup.inflate(itemHolder: Int): View =
  LayoutInflater.from(context).inflate(itemHolder, this, false)

fun String.fromHtml() = HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)

fun GoogleMap.moveToLatLng(latLng: LatLng, zoom: Float) {
  moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
}

fun GoogleMap.animateToLatLng(latLng: LatLng, zoom: Float) {
  animateCamera(CameraUpdateFactory.newCameraPosition(CameraPosition.fromLatLngZoom(latLng, zoom)))
}

fun AutoCompleteTextView.clearText() {
  setText("")
}

fun GoogleMap.disableMapButtons() {
  uiSettings.isMyLocationButtonEnabled = false
  uiSettings.isMapToolbarEnabled = false
  uiSettings.isCompassEnabled = false
  uiSettings.isIndoorLevelPickerEnabled = false
}
