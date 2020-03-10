package org.galio.bussantiago.features.stops

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

private const val REQUEST_LOCATION_PERMISSION = 1

class BusStopsMapFragment : SupportMapFragment(), OnMapReadyCallback {

  private lateinit var mGoogleMap: GoogleMap

  companion object {
    private const val LINE_ID_KEY = "line_id_key"
    private const val ROUTE_NAME_KEY = "route_name_key"
    fun newInstance(id: Int, routeName: String): BusStopsMapFragment {
      val fragment = BusStopsMapFragment()
      val bundle = Bundle()
      bundle.putInt(LINE_ID_KEY, id)
      bundle.putString(ROUTE_NAME_KEY, routeName)
      fragment.arguments = bundle
      return fragment
    }
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    val lineId = arguments?.get(LINE_ID_KEY) as Int
    val routeName = arguments?.get(ROUTE_NAME_KEY) as String

    getMapAsync(this)
  }

  override fun onMapReady(googleMap: GoogleMap?) {
    if (googleMap != null) {
      mGoogleMap = googleMap
      // Add a marker in Home and move the camera
      val home = LatLng(42.567595, -8.988767)
      val zoom = 15f
      mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(home, zoom))
      mGoogleMap.addMarker(MarkerOptions()
          .position(home)
          .title("Code Stop")
          .snippet("Name Stop")
      )
      enableMyLocation()
    }
  }

  private fun enableMyLocation() {
    if (ContextCompat.checkSelfPermission(context!!,
        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
      mGoogleMap.isMyLocationEnabled = true
    } else {
      ActivityCompat.requestPermissions(activity!!,
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
    }
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<String?>,
    grantResults: IntArray
  ) {
    // Check if location permissions are granted and if so enable the location data layer.
    when (requestCode) {
      REQUEST_LOCATION_PERMISSION ->
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          enableMyLocation()
        }
    }
  }
}
