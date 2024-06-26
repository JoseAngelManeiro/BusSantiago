package org.galio.bussantiago.features.stops.map

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import org.galio.bussantiago.R
import org.galio.bussantiago.common.handleException
import org.galio.bussantiago.common.model.BusStopModel
import org.galio.bussantiago.common.navigateSafe
import org.galio.bussantiago.features.stops.BusStopsArgs
import org.galio.bussantiago.features.times.TimesDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class BusStopsMapFragment : SupportMapFragment(), OnMapReadyCallback {

  private val viewModel: BusStopsMapViewModel by viewModel()

  private lateinit var busStopsArgs: BusStopsArgs
  private var mGoogleMap: GoogleMap? = null
  private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

  companion object {
    private const val BUS_STOPS_ARGS_KEY = "bus_stops_args_key"
    fun newInstance(busStopsArgs: BusStopsArgs): BusStopsMapFragment {
      val fragment = BusStopsMapFragment()
      val bundle = Bundle()
      bundle.putParcelable(BUS_STOPS_ARGS_KEY, busStopsArgs)
      fragment.arguments = bundle
      return fragment
    }
  }

  override fun onCreate(bundle: Bundle?) {
    super.onCreate(bundle)

    requestPermissionLauncher =
      registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
          enableMyLocation()
        }
      }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    busStopsArgs = arguments?.get(BUS_STOPS_ARGS_KEY) as BusStopsArgs

    getMapAsync(this)

    viewModel.lineMapModel.observe(viewLifecycleOwner) { resource ->
      resource.fold(
        onError = { handleException(it) },
        onSuccess = { setUpMap(it) }
      )
    }
  }

  override fun onMapReady(googleMap: GoogleMap) {
    mGoogleMap = googleMap
    enableMyLocation()
    mGoogleMap?.setOnInfoWindowClickListener { marker ->
      if (marker.title != null && marker.snippet != null) {
        navigateSafe(
          R.id.actionShowTimes,
          TimesDialogFragment.createArguments(BusStopModel(marker.title!!, marker.snippet!!))
        )
      }
    }
    viewModel.load(busStopsArgs)
  }

  private fun setUpMap(lineMapModel: LineMapModel) {
    val polylineOptions = PolylineOptions()

    val busStopMapModels = lineMapModel.busStopMapModels

    busStopMapModels.forEachIndexed { index, busStopMapModel ->
      var hue = BitmapDescriptorFactory.HUE_RED
      if (index == 0 || index == busStopMapModels.lastIndex) {
        hue = BitmapDescriptorFactory.HUE_BLUE
      }

      val latLng = LatLng(
        busStopMapModel.coordinates.latitude,
        busStopMapModel.coordinates.longitude
      )

      polylineOptions.add(latLng)

      mGoogleMap?.addMarker(
        MarkerOptions()
          .position(latLng)
          .title(busStopMapModel.code)
          .snippet(busStopMapModel.name)
      )?.setIcon(BitmapDescriptorFactory.defaultMarker(hue))
    }

    polylineOptions.color(Color.parseColor(lineMapModel.lineStyle))

    mGoogleMap?.addPolyline(polylineOptions)

    val firstBusStopMapModel = lineMapModel.busStopMapModels.first()
    val firstLatLng = LatLng(
      firstBusStopMapModel.coordinates.latitude,
      firstBusStopMapModel.coordinates.longitude
    )
    mGoogleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(firstLatLng, 13f))
  }

  private fun enableMyLocation() {
    if (ContextCompat.checkSelfPermission(
        requireContext(),
        Manifest.permission.ACCESS_FINE_LOCATION
      ) == PackageManager.PERMISSION_GRANTED
    ) {
      mGoogleMap?.isMyLocationEnabled = true
    } else {
      requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }
  }
}
