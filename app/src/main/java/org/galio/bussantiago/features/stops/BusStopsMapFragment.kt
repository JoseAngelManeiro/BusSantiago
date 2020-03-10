package org.galio.bussantiago.features.stops

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import org.galio.bussantiago.R
import org.galio.bussantiago.common.Status
import org.galio.bussantiago.common.handleException
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.common.navigateSafe
import org.galio.bussantiago.features.times.TimesFragment
import org.koin.android.viewmodel.ext.android.viewModel

private const val REQUEST_LOCATION_PERMISSION = 1

class BusStopsMapFragment : SupportMapFragment(), OnMapReadyCallback {

  private val viewModel: BusStopsMapViewModel by viewModel()

  private var lineId: Int = 0
  private lateinit var routeName: String
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

    lineId = arguments?.get(LINE_ID_KEY) as Int
    routeName = arguments?.get(ROUTE_NAME_KEY) as String

    getMapAsync(this)

    viewModel.lineMapModel.observe(viewLifecycleOwner, Observer {
      it?.let { resourceLineMapModel ->
        when (resourceLineMapModel.status) {
          Status.LOADING -> {}
          Status.SUCCESS -> {
            setUpMap(resourceLineMapModel.data!!)
          }
          Status.ERROR -> {
            handleException(resourceLineMapModel.exception!!)
          }
        }
      }
    })
  }

  override fun onMapReady(googleMap: GoogleMap?) {
    if (googleMap != null) {
      mGoogleMap = googleMap
      enableMyLocation()
      mGoogleMap.setOnInfoWindowClickListener {
        navigateSafe(R.id.actionShowTimesFragment,
          TimesFragment.createArguments(BusStopModel(it.title, it.snippet)))
      }
      viewModel.load(lineId, routeName)
    }
  }

  private fun setUpMap(lineMapModel: LineMapModel) {
    val polylineOptions = PolylineOptions()

    val busStopMapModels = lineMapModel.busStopMapModels

    busStopMapModels.forEachIndexed { index, busStopMapModel ->
      var hue = BitmapDescriptorFactory.HUE_RED
      if (index == 0 || index == busStopMapModels.lastIndex) {
        hue = BitmapDescriptorFactory.HUE_BLUE
      }

      val latLng = LatLng(busStopMapModel.coordinates.latitude,
        busStopMapModel.coordinates.longitude)

      polylineOptions.add(latLng)

      mGoogleMap.addMarker(MarkerOptions()
        .position(latLng)
        .title(busStopMapModel.code)
        .snippet(busStopMapModel.name))
        .setIcon(BitmapDescriptorFactory.defaultMarker(hue))
    }

    polylineOptions.color(Color.parseColor(lineMapModel.lineStyle))

    mGoogleMap.addPolyline(polylineOptions)

    val firstBusStopMapModel = lineMapModel.busStopMapModels.first()
    val firstLatLng = LatLng(firstBusStopMapModel.coordinates.latitude,
      firstBusStopMapModel.coordinates.longitude)
    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLatLng, 14f))
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
